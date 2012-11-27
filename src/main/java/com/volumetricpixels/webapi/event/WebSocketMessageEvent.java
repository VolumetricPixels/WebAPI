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
