/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.wolftein.webapi.protocol;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.jboss.netty.util.CharsetUtil;

import com.wolftein.webapi.WebPlugin;
import com.wolftein.webapi.event.WebRequestEvent;
import com.wolftein.webapi.event.WebSocketConnectEvent;
import com.wolftein.webapi.event.WebSocketDisconnectEvent;
import com.wolftein.webapi.event.WebSocketMessageEvent;

/**
 * {@see SimpleChannelUpstreamHandler} for {@see WebSocketServer}
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebServerHandler extends SimpleChannelUpstreamHandler {
	/**
	 * The plugin instance
	 */
	private WebPlugin plugin = WebPlugin.getInstance();
	/**
	 * The handshaker of the WebSocket
	 */
	private WebSocketServerHandshaker handshaker;
	/**
	 * The list of websocket clients
	 */
	private Map<Integer, ChannelHandlerContext> clientList = new HashMap<Integer, ChannelHandlerContext>();

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		Object message = e.getMessage();
		if (message instanceof WebSocketFrame && plugin.isWebSocketEnabled())
			handleWebSocketFrame(ctx, (WebSocketFrame) message);
		else if (message instanceof HttpRequest)
			handleHttpRequest(ctx, (HttpRequest) message);
		else
			ctx.getChannel().close();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		// We are closing a WebSocket client
		if (!clientList.containsKey(ctx.getChannel().getId())) {
			return;
		}
		clientList.remove(ctx.getChannel().getId());
		WebSocketDisconnectEvent connectionEvent = new WebSocketDisconnectEvent(
				ctx.getChannel(), ctx.getAttachment());
		plugin.getEngine().getEventManager().callDelayedEvent(connectionEvent);
	}

	/**
	 * Handle any HTML request as a WebSocket handshake.
	 * 
	 * @param ctx
	 *            the client context
	 * @param request
	 *            the frame data recieved
	 * @throws NoSuchAlgorithmException
	 */
	public void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest request) {
		// Handle WebSocket Handshake
		if (request.getMethod() == HttpMethod.GET
				&& request.getUri().equals(plugin.getWebSocketUri())) {
			WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
					getWebSocketLocation(request), null, false);
			handshaker = wsFactory.newHandshaker(request);
			if (handshaker == null) {
				wsFactory.sendUnsupportedWebSocketVersionResponse(ctx
						.getChannel());
				return;
			} else {
				handshaker.handshake(ctx.getChannel(), request);
			}
			// Call the connect event
			WebSocketConnectEvent connectionEvent = new WebSocketConnectEvent(
					ctx.getChannel());
			connectionEvent = plugin.getEngine().getEventManager()
					.callEvent(connectionEvent);
			if (connectionEvent.isCancelled()) {
				ctx.getChannel().write(new CloseWebSocketFrame());
			} else {
				ctx.setAttachment(connectionEvent.getAttachment());
				clientList.put(ctx.getChannel().getId(), ctx);
			}
			return;
		}
		// Handle HTTP request
		WebRequestEvent event = new WebRequestEvent(ctx.getChannel(), request);
		event = plugin.getEngine().getEventManager().callEvent(event);
		sendHttpResponse(ctx, request, event.getResponse());
	}

	/**
	 * Handle any WebSocket frame
	 * 
	 * @param ctx
	 *            the client context
	 * @param frame
	 *            the frame data recieved
	 */
	public void handleWebSocketFrame(ChannelHandlerContext ctx,
			WebSocketFrame frame) {

		// Check for closing frame or ping-pong frame
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.getChannel(), (CloseWebSocketFrame) frame);
			return;
		} else if (frame instanceof PingWebSocketFrame) {
			ctx.getChannel().write(
					new PongWebSocketFrame(frame.getBinaryData()));
			return;
		}

		// Call the event to deliver the message
		WebSocketMessageEvent event = new WebSocketMessageEvent(
				ctx.getChannel(), ctx.getAttachment(), frame);
		plugin.getEngine().getEventManager().callDelayedEvent(event);
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		ctx.getChannel().close();
	}

	/**
	 * Send a HTML response to the channel
	 * 
	 * @param ctx
	 *            the channel context
	 * @param req
	 *            the request
	 * @param res
	 *            the response of the request
	 */
	public void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req,
			HttpResponse res) {
		// Generate an error page
		if (res.getStatus().getCode() != 200) {
			res.setContent(ChannelBuffers.copiedBuffer(res.getStatus()
					.toString(), CharsetUtil.UTF_8));
			HttpHeaders.setContentLength(res, res.getContent().readableBytes());
		}
		// Send the response to the client, and close if we had to
		ChannelFuture f = ctx.getChannel().write(res);
		if (!HttpHeaders.isKeepAlive(req) || res.getStatus().getCode() != 200)
			f.addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * Return the WebSocket servlet location
	 * 
	 * @param req
	 *            remote request
	 * @return the location of the WebSocket servlet
	 */
	private String getWebSocketLocation(HttpRequest req) {
		return "ws://" + req.getHeader(HttpHeaders.Names.HOST)
				+ plugin.getWebSocketUri();
	}
}
