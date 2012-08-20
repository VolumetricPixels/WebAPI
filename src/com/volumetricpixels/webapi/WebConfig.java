/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.volumetricpixels.webapi;

import java.io.File;

import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

/**
 * Contain all the information regarding the plugin
 * 
 * @author Agustin Alvarez <agustin.l.alvarez@hotmail.com>
 */
public class WebConfig extends ConfigurationHolderConfiguration {

	/**
	 * The port where the server will listen
	 */
	public static final ConfigurationHolder LISTENING_PORT = new ConfigurationHolder(
			"81", "listening-port");
	/**
	 * Is the websocket enabled
	 */
	public static final ConfigurationHolder WEBSOCKET_ENABLED = new ConfigurationHolder(
			"true", "websocket-enabled");
	/**
	 * The location of the websocket
	 */
	public static final ConfigurationHolder WEBSOCKET_LOCATION = new ConfigurationHolder(
			"/websocket", "websocket-uri");
	/**
	 * Is rocky client enabled?
	 */
	public static final ConfigurationHolder ROCKY_ENABLED = new ConfigurationHolder(
			"true", "rocky-enabled");
	/**
	 * The server port
	 */
	public static final ConfigurationHolder ROCKY_PORT = new ConfigurationHolder(
			"25565", "rocky-server-port");

	/**
	 * Default constructor of the configuration class
	 */
	public WebConfig() {
		super(new YamlConfiguration(new File("plugins/WebAPI/setting.yml")));
	}

	/**
	 * Load the configuration of the plugin
	 */
	public void load() {
		File dir = new File("plugins/WebAPI");
		if (!dir.exists())
			dir.mkdir();
		try {
			super.load();
			super.save();
		} catch (ConfigurationException ex) {
			WebPlugin.getInstance().printMessage(
					"Can not create configuration file setting.yml");
		}
	}

}
