/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.volumetricpixels.webapi.rocky;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * {@see SimpleChannelUpstreamHandler} for {@see WebSocketClient}
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebRockyHandler extends SimpleChannelUpstreamHandler {

	/**
	 * The client owner of the handler
	 */
	protected WebRockyClient client;

	/**
	 * Default constructor
	 * 
	 * @param client
	 *            the owner of the handler
	 */
	public WebRockyHandler(WebRockyClient client) {
		this.client = client;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		client.sendToProxy((byte[]) e.getMessage());
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		client.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getChannel().close();
	}

}
