/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.wolftein.webapi;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.spout.api.Spout;
import org.spout.api.UnsafeMethod;
import org.spout.api.plugin.CommonPlugin;

import com.wolftein.webapi.protocol.WebServerPipelineFactory;
import com.wolftein.webapi.rocky.WebRockyListener;

/**
 * Spout plugin base implementation
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebPlugin extends CommonPlugin {
	/**
	 * The configuration file of the plugin
	 */
	protected WebConfig config;
	/**
	 * The channel listener of the socket
	 */
	protected Channel channel;

	/**
	 * {@inhericDoc}
	 */
	@Override
	@UnsafeMethod
	public void onDisable() {
		// Close the channel if we had to
		if (channel == null || !channel.isOpen()) {
			return;
		}
		channel.close();
		printMessage("The listener has been closed");
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	@UnsafeMethod
	public void onLoad() {
		this.config = new WebConfig();
		this.config.load();
	}

	/**
	 * {@inhericDoc}
	 */
	@Override
	@UnsafeMethod
	public void onEnable() {
		// Create the listener bootstrap
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new WebServerPipelineFactory());

		// Set some TCP optimization for any WebSocket
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);

		// Spawn the listener for any remote connection
		int socketPort = config.getChild("listening-port").getInt();
		channel = bootstrap.bind(new InetSocketAddress(socketPort));
		printMessage("The listener has been spawn on port '" + socketPort + "'");
		if (config.getChild("websocket-enabled").getBoolean())
			printMessage("Enabled WebSocket connections spawned at /websocket");

		// Enable rocky listener?
		if (config.getChild("rocky-enabled").getBoolean()) {
			getEngine().getEventManager().registerEvents(
					new WebRockyListener(), this);
			printMessage("Rocky client has been enabled");
		}
	}

	/**
	 * Return if the websocket is enabled
	 * 
	 * @return if the websocket is enabled
	 */
	public boolean isWebSocketEnabled() {
		return config.getChild("websocket-enabled").getBoolean();
	}

	/**
	 * Return the URI location of the websocket
	 * 
	 * @return the URI location of the websocket
	 */
	public String getWebSocketUri() {
		return config.getChild("websocket-uri").getString();
	}

	/**
	 * Return the port where the server is listening
	 * 
	 * @return the port where the server is listening
	 */
	public int getServerPort() {
		return config.getChild("rocky-server-port").getInt();
	}

	/**
	 * Print message to the server console
	 * 
	 * @param message
	 *            the message to print
	 */
	public void printMessage(String message) {
		Logger.getLogger("WebAPI").info("[WebAPI] " + message);
	}

	/**
	 * Return the instance of the plugin
	 * 
	 * @return the instance of the plugin
	 */
	public static WebPlugin getInstance() {
		return (WebPlugin) Spout.getPluginManager().getPlugin("WebAPI");
	}

}
