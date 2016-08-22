package com.ivstuart.tmud.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionManager {

	private static final Logger LOGGER = LogManager.getLogger();

	private static Map<SocketChannel, Connection> map = new HashMap<SocketChannel, Connection>();

	public static void add(SocketChannel sc) {

		if (map.containsKey(sc)) {

			LOGGER.error("Duplicate socketChannel in ConnectionManager");

		}

		map.put(sc, new Connection(sc));
	}

	public static void clear() {
		map.clear();
	}

	public static void process(SocketChannel sc, String input) {
		map.get(sc).process(input);
	}

	public static void remove(SocketChannel sc) {
		map.remove(sc);
	}

	private ConnectionManager() {

	}

	public static void close(SelectionKey sk) {
		map.remove(sk.channel());
		try {
			sk.cancel();
			sk.channel().close();
			SocketChannel socketChannel = (SocketChannel)sk.channel();
			if (socketChannel.isConnected()) {
				socketChannel.finishConnect();
			}
		} catch (IOException e) {
			LOGGER.error("Problem closing channel",e);
		}
	}

}
