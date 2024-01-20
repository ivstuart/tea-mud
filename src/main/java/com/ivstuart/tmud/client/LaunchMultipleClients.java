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

package com.ivstuart.tmud.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Ivan on 12/09/2016.
 * <p>
 * This class only works when option 8 is enabled for Login class.
 */
public class LaunchMultipleClients {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] argv) throws IOException {

        LaunchClient client1 = launchClient();
        for (int index = 0; index < 100; index++) {
            launchClient();
        }

        client1.readWriteStreams();

    }

    private static LaunchClient launchClient() throws IOException {
        LaunchClient client = new LaunchClient();
        client.start();
        client.send("8");

        sleep();
        client.send("follow 1.");
        return client;
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            LOGGER.error("Problem sleeping:" + e);
        }
    }
}
