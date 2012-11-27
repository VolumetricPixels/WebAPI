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
/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.volumetricpixels.webapi.event;

import org.jboss.netty.channel.Channel;
import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;

/**
 * Encapsulate the event when websocket is disconnected
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebSocketDisconnectEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	private Channel channel;
	private Object attachment;

	/**
	 * Constructor of the event
	 * 
	 * @param channel
	 *            the channel of the connection
	 */
	public WebSocketDisconnectEvent(Channel channel, Object attachment) {
		this.channel = channel;
		this.attachment = attachment;
	}

	/**
	 * Return the channel of the connection
	 * 
	 * @return the channel of the connection
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Return the attachment of the channel
	 * 
	 * @return the attachment of the channel
	 */
	public Object getAttachment() {
		return attachment;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * {@see Event#getHandlers()}
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}
}