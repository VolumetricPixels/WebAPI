/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.volumetricpixels.webapi.rocky;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * Encapsulate the {@see ChannelPipelineFactory} of {@see WebRockyClient}
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebRockyClientPipelineFactory implements ChannelPipelineFactory {
	/**
	 * The owner of the factory
	 */
	protected WebRockyClient client;

	/**
	 * Default constructor
	 * 
	 * @param client
	 *            the owner of the factory
	 */
	public WebRockyClientPipelineFactory(WebRockyClient client) {
		this.client = client;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		return Channels.pipeline(new WebRockyHandler(client));
	}

}
