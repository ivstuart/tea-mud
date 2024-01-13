package com.ivstuart.tmud.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

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
