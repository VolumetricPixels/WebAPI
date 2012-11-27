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
package com.volumetricpixels.webapi.rocky;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;

import com.volumetricpixels.webapi.event.WebSocketDisconnectEvent;
import com.volumetricpixels.webapi.event.WebSocketMessageEvent;

/**
 * Encapsulate the listener for the JS WebGL client Rocky
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebRockyListener implements Listener {

	protected Map<Channel, WebRockyClient> clientList = new HashMap<Channel, WebRockyClient>();

	/**
	 * Handle when the client has been closed
	 * 
	 * @param event
	 *            the data of the event
	 */
	@EventHandler(order = Order.MONITOR)
	public void onSocketClose(WebSocketDisconnectEvent event) {
		Channel channel = event.getChannel();
		if (!clientList.containsKey(channel)) {
			return;
		}
		clientList.get(channel).close();
		clientList.remove(channel);
	}

	/**
	 * Handle when the client send a message
	 * 
	 * @param event
	 *            the data of the event
	 */
	@EventHandler(order = Order.MONITOR)
	public void onSocketMessage(WebSocketMessageEvent event) {
		WebSocketFrame frame = event.getRequest();
		Channel channel = event.getChannel();

		if (frame instanceof TextWebSocketFrame) {
			TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
			if (!textFrame.getText().equals("#RockAuth")) {
				return;
			}
			clientList.put(channel, new WebRockyClient(channel));
			event.setCancelled(true);
		} else if (!clientList.containsKey(channel)) {
			return;
		}

		clientList.get(channel).sendToWorker(event.getRequest());
		event.setCancelled(true);
	}
}
