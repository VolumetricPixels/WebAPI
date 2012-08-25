/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.volumetricpixels.webapi.protocol;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.jboss.netty.util.CharsetUtil;

import com.volumetricpixels.webapi.WebPlugin;
import com.volumetricpixels.webapi.event.WebSocketConnectEvent;
import com.volumetricpixels.webapi.event.WebSocketDisconnectEvent;
import com.volumetricpixels.webapi.event.WebSocketMessageEvent;


/**
 * {@see SimpleChannelUpstreamHandler} for {@see WebSocketClient}
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebClientHandler  extends SimpleChannelUpstreamHandler {
	/**
	 * The plugin instance
	 */
	private WebPlugin plugin = WebPlugin.getInstance();
	/**
	 * The handshaker of the WebSocket
	 */
	private WebSocketClientHandshaker handshaker;
    
	/**
	 * {@inhericDoc}
	 * @throws Exception 
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		// Do the handshake of the websocket
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ctx.getChannel(), (HttpResponse) e);
			WebSocketConnectEvent connectionEvent = new WebSocketConnectEvent(
					ctx.getChannel());
			connectionEvent = plugin.getEngine().getEventManager()
					.callEvent(connectionEvent);
            return;
        }
		// We don't allow HTTP response
        if (e instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) e;
            throw new Exception("Unexpected HttpResponse (status=" + response.getStatus() + ", content="
                    + response.getContent().toString(CharsetUtil.UTF_8) + ")");
        }
        
        handleWebSocketFrame(ctx, (WebSocketFrame)e);
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
			ctx.getChannel().close();
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
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		WebSocketDisconnectEvent connectionEvent = new WebSocketDisconnectEvent(
				ctx.getChannel(), ctx.getAttachment());
		plugin.getEngine().getEventManager().callDelayedEvent(connectionEvent);
	}
	
	/**
	 * {@inhericDoc}
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		ctx.getChannel().close();
	}
}
