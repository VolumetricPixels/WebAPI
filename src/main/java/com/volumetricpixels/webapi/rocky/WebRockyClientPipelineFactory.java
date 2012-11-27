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
