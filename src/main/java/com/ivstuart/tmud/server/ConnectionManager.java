/*
 *  Copyright 2024. Ivan Stuart
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<SocketChannel, Connection> SOCKET_CHANNEL_CONNECTION_MAP = new HashMap<>();

    private ConnectionManager() {
    }

    public static void add(SocketChannel socketChannel) {

        if (SOCKET_CHANNEL_CONNECTION_MAP.containsKey(socketChannel)) {
            LOGGER.error("Duplicate socketChannel in ConnectionManager not adding connection");
            return;
        }

        SOCKET_CHANNEL_CONNECTION_MAP.put(socketChannel, new Connection(socketChannel));
    }

    public static Connection getConnection(SocketChannel socketChannel) {
        return SOCKET_CHANNEL_CONNECTION_MAP.get(socketChannel);
    }


    public static Connection remove(SocketChannel socketChannel) {
        return SOCKET_CHANNEL_CONNECTION_MAP.remove(socketChannel);
    }

    public static Collection<Connection> getConnections() {
        return SOCKET_CHANNEL_CONNECTION_MAP.values();
    }
}
