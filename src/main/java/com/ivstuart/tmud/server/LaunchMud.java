/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.server;

import com.ivstuart.tmud.state.util.StateReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author stuarti
 */
public class LaunchMud {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Properties mudServerProperties;


    private static boolean isRunning = true;

    private static void displayUsage() {
        System.out.println("LaunchMud <primary config file> [mode]");
        System.exit(0);
    }

    public static void main(String[] argv) {

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

        MudServer mudServer = MudServer.getInstance();

        mudServer.startListening(getMudServerPort());

        while (isRunning) {
            try {
                Thread.sleep(3000); // Check for shutdown every three seconds
            } catch (Exception e) {
                LOGGER.error("Problem sleeping", e);
            }
        }


        LOGGER.info("Finished mud.");
    }

    public static boolean stop() {
        if (isRunning) {
            MudServer.getInstance().stop();
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
        return Integer.parseInt(mudServerProperties.getProperty("default.port", "5678"));
    }

    public static String getMudServerConfigDir() {
        return mudServerProperties.getProperty("command.config.dir", "/src/main/resources/config/");
    }

    public static String getMudServerClassPrefix() {
        return mudServerProperties.getProperty("class.prefix", "com.ivstuart.tmud.");
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