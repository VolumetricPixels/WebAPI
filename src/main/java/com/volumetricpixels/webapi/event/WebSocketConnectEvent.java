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
package com.volumetricpixels.webapi.event;

import org.jboss.netty.channel.Channel;
import org.spout.api.event.Cancellable;
import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;

/**
 * Encapsulate the event when a websocket connect
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebSocketConnectEvent extends Event implements Cancellable {

	private static HandlerList handlers = new HandlerList();
	private boolean isCancelled = false;
	private Channel channel;
	private Object attachment;
	
	/**
	 * Constructor of the event
	 * 
	 * @param channel
	 *            the channel of the connection
	 */
	public WebSocketConnectEvent(Channel channel) {
		this.channel = channel;
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
	 * Set the attachment of the channel
	 * 
	 * @param attachment the new attachment of the channel
	 */
	public <J> void setAttachment(J attachment) {
		this.attachment = attachment;
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
	public void setCancelled(boolean value) {
		isCancelled = value;
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	public boolean isCancelled() {
		return isCancelled;
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