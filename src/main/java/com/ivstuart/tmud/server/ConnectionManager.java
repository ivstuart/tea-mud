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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

	private static final Logger LOGGER = LogManager.getLogger();
	private static Map<SocketChannel, Connection> map = new HashMap<SocketChannel, Connection>();

    private ConnectionManager() {

    }

    public static Map<SocketChannel, Connection> getMap() {
        return map;
    }

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
