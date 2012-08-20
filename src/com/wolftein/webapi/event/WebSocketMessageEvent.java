/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.wolftein.webapi.event;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.spout.api.event.Cancellable;
import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;

/**
 * Encapsulate the event when a message from a websocket is recieved
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebSocketMessageEvent extends Event implements Cancellable {

	private static HandlerList handlers = new HandlerList();
	private Channel channel;
	private WebSocketFrame request;
	private Object attachment;
	private boolean isCancelled;
	
	/**
	 * Constructor of the event
	 * 
	 * @param channel
	 *            the channel of the event
	 * @param attachment
	 *            the attachment of the channel
	 * @param request
	 *            the request of the event
	 */
	public WebSocketMessageEvent(Channel channel, Object attachment,
			WebSocketFrame request) {
		this.channel = channel;
		this.request = request;
		this.attachment = attachment;
	}

	/**
	 * Return the channel of the event
	 * 
	 * @return the channel of the event
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
	 * Return the request of the event
	 * 
	 * @return the request of the event
	 */
	public WebSocketFrame getRequest() {
		return this.request;
	}
	
	/**
	 * Return the request of the event as a text frame
	 * 
	 * @return the request of the event as a text frame
	 */
	public TextWebSocketFrame getRequestAsText() {
		return (TextWebSocketFrame)this.request;
	}
	
	/**
	 * Return the request of the event as a binary frame
	 * 
	 * @return the request of the event as a binary frame
	 */
	public BinaryWebSocketFrame getRequestAsBinary() {
		return (BinaryWebSocketFrame)this.request;
	}
	
	/**
	 * {@inhericDoc}
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	/**
	 * {@inhericDoc}
	 */
	@Override
	public void setCancelled(boolean value) {
		this.isCancelled = value;
	}
	
	/**
	 * {@inhericDoc}
	 */
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}
	
	/**
	 * {@see Event#getHandlers()}
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
