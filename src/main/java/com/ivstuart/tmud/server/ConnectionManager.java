package com.ivstuart.tmud.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ConnectionManager {

	private static final Logger LOGGER = Logger
			.getLogger(ConnectionManager.class);

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
			((SocketChannel)sk.channel()).finishConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
