/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.client;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ivan on 12/09/2016.
 * <p>
 * This class only works when option 8 is enabled for Login class.
 */
public class LaunchMultipleClients {

    private static List<LaunchClient> clients;

    public static void main(String argv[]) throws IOException {

        LaunchClient client1 = launchClient();
        for (int index = 0; index < 100; index++) {
            launchClient();
        }

        client1.readWriteStreams();

    }

    private static LaunchClient launchClient() throws IOException {
        LaunchClient client = LaunchClient.init();
        //client.readWriteStreams();
        client.send("8");

        sleep();
        client.send("follow 1.");
        return client;
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
