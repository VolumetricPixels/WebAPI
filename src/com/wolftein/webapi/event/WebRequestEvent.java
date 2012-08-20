/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.wolftein.webapi.event;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.spout.api.event.Event;
import org.spout.api.event.HandlerList;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.util.CharsetUtil;

/**
 * Encapsulate the event when a user request a page
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebRequestEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	private Channel channel;
	private HttpRequest request;
	private HttpResponse response;

	/**
	 * Constructor of the event
	 * 
	 * @param channel
	 *            the channel of the event
	 */
	public WebRequestEvent(Channel channel, HttpRequest request) {
		this.channel = channel;
		this.request = request;
		this.response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.FORBIDDEN);
	}

	/**
	 * Set the buffer content of the response
	 * 
	 * @param buffer
	 *            the buffer
	 */
	public void setContent(ChannelBuffer buffer) {
		this.response.setContent(buffer);
	}

	/**
	 * Set the content of the response as a raw string
	 * 
	 * @param content
	 *            the content of the response
	 */
	public void setContent(String content) {
		setContent(ChannelBuffers.copiedBuffer(content, CharsetUtil.UTF_8));
	}

	/**
	 * Set the version of the response
	 * 
	 * @param version
	 *            the version of the response
	 */
	public void setVersion(HttpVersion version) {
		this.response.setProtocolVersion(version);
	}

	/**
	 * Set the response status of the response
	 * 
	 * @param status
	 *            the response status
	 */
	public void setResponseStatus(HttpResponseStatus status) {
		this.response.setStatus(status);
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
	 * Return the request of the event
	 * 
	 * @return the request of the event
	 */
	public HttpRequest getRequest() {
		return this.request;
	}

	/**
	 * Set the response of the request
	 * 
	 * @param response
	 *            the response of the request
	 */
	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	/**
	 * Return the current response
	 * 
	 * @return the current response
	 */
	public HttpResponse getResponse() {
		return response;
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