/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.server;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.state.util.StateReader;

/**
 * @author stuarti
 * 
 */
public class LaunchMud {

	private static final Logger LOGGER = Logger.getLogger(LaunchMud.class);

	public static Properties mudServerProperties;

	private static void displayUsage() {
		System.out.println("LaunchMud <primary config file>");
		System.exit(0);
	}

	public static void main(String argv[]) {

		LOGGER.info("Starting mud.");

		if (argv.length > 1) {
			displayUsage();
		}

		try {
			loadMudServerProperties();
		} catch (Exception e) {
			LOGGER.error("Problem loading mud server properties", e);
		}

		StateReader.getInstance().load();

		MudServer s = new MudServer();

		s.startListening(mudServerProperties.getProperty("default.port","5678"));
		
		try {
			Thread.sleep(15 * 60 * 1000); // 15 minutes
		} catch (Exception e) {
			LOGGER.error("Problem sleeping",e);
		}

		LOGGER.info("Finnished mud.");

	}

	public static void loadMudServerProperties() throws URISyntaxException,
			IOException {
		LOGGER.info("Loading mud server properties");

		mudServerProperties = new Properties();

		Reader reader = null;

		try {

			reader = new FileReader("src/main/resources/config/mudserver.properties");

			mudServerProperties.load(reader);

		} finally {

			if (reader != null) {
				reader.close();
			}

		}

	}

}