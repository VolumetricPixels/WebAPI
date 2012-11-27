/*
 * This file is part of WebAPI.
 *
 * Copyright (c) 2012-2012, VolumetricPixels <http://volumetricpixels.com/>
 * WebAPI is licensed under the VolumetricPixels License Version 1.
 *
 * The VolumetricPixels License is a triple license combined out of the AGPL v3
 * License, the MIT License and the Classpath Exception License.
 *
 * You should have received a copy of the GNU Affero General Public License,
 * the MIT license and the VolumetricPixels License v1 along with this program.
 * If not, see <http://github.com/VolumetricPixels/Vitals/blob/master/License.txt>
 * for the full license.
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
