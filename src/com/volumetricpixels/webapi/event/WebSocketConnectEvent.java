/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
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