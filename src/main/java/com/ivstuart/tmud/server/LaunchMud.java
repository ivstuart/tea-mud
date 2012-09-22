/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.server;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.state.util.StateReader;

/**
 * @author stuarti
 * 
 */
public class LaunchMud {

	private static final Logger LOGGER = Logger.getLogger(LaunchMud.class);

	public final static int DEFAULT_PORT = 5678;
	public final static String USAGE = "LaunchMud <primary config file>";

	private static void displayUsage() {
		System.out.println(USAGE);
		System.exit(0);
	}

	public static void main(String argv[]) {

		LOGGER.info("Starting mud.");

		if (argv.length > 1) {
			displayUsage();
		}

		StateReader.load();

		MudServer s = new MudServer();

		s.startListening(DEFAULT_PORT);
		try {
			Thread.sleep(15 * 60 * 1000); // 15 minutes
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		LOGGER.info("Finnished mud.");

	}

}