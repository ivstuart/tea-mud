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
	
	private static MudServer mudServer;
	
	private static boolean isRunning = true;

	private static void displayUsage() {
		System.out.println("LaunchMud <primary config file> [mode]");
		System.exit(0);
	}

	public static void main(String argv[]) {

		LOGGER.info("Starting mud.");

		if (argv.length > 1) {
			displayUsage();
		}

		start();

	}

	public static void start() {
		try {
			loadMudServerProperties();
		} catch (Exception e) {
			LOGGER.error("Problem loading mud server properties", e);
		}

		StateReader.getInstance().load();

		mudServer = new MudServer();

		mudServer.startListening(getMudServerPort());
		
		while (isRunning) {
			try {
				Thread.sleep(3000); // Check for shutdown every three seconds
			} catch (Exception e) {
				LOGGER.error("Problem sleeping",e);
			}
		}


		LOGGER.info("Finnished mud.");
	}
	
	public static boolean stop() {
		if (mudServer != null) {
			mudServer.stop();
			isRunning = false;
			return true;
		}
		return false;
		
	}
	
	public static boolean isRunning() {
		return isRunning;
	}

	public static int getMudServerPort() {
		if (mudServerProperties == null) {
			return 5678;
		}
		return Integer.parseInt(mudServerProperties.getProperty("default.port","5678"));
	}

	public static String getMudServerConfigDir() {
		return mudServerProperties.getProperty("command.config.dir","/src/main/resources/config/");
	}

	public static String getMudServerClassPrefix(){
		return mudServerProperties.getProperty("class.prefix","com.ivstuart.tmud.");
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