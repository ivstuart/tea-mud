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

package com.ivstuart.tmud.server;

import com.ivstuart.tmud.client.LaunchClient;
import com.ivstuart.tmud.command.DummyConnection;
import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Ivan on 28/09/2016.
 */
public class MudServerTest {

    @Test
    public void testReading() {
        MudServer mudServer = new MudServer();
        mudServer.startListening(5678);

        LaunchClient client = null;
        try {
            client = new LaunchClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.send("1");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Connection connection : ConnectionManager.getConnections()) {
            connection.out("hello");
        }

    }
}
