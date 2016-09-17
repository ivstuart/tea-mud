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

import com.ivstuart.tmud.exceptions.MudException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * This example demonstrate use of multiple Selectors() with a single SocketChannel.
 * There are two threads running Selectors() each registered for READ and WRITE respectively
 */
public class MudServer {

	private static final Logger LOGGER = LogManager.getLogger();
	/**
	 * Single selector for accepts, reads
	 */
	private Selector readSelector = null;
	/**
	 * Single selector for writes
	 */
	private Selector writeSelector = null;
	/**
	 * ServerSocketChannel which listens for client connections
	 */
	private ServerSocketChannel ssch = null;

	protected void startListening(String port) {
		startListening(Integer.parseInt(port));
	}

	/**
	 * Sets up the selectors and starts listening
     * @param port
     */
    protected void startListening(int port) {
		LOGGER.info("Starting read write networking for the mud server");
		try {
			// create the selector and the ServerSocket
			readSelector = SelectorProvider.provider().openSelector();
			writeSelector = SelectorProvider.provider().openSelector();
			ssch = ServerSocketChannel.open();

			// Non-blocking
			ssch.configureBlocking(false);
			InetSocketAddress isa = new InetSocketAddress(
					InetAddress.getLocalHost(), port);

			LOGGER.info("Started listening on [ " + InetAddress.getLocalHost()
					+ ":" + port + " ]");

			ssch.socket().bind(isa);
			ssch.register(readSelector, SelectionKey.OP_ACCEPT);
			ssch.register(writeSelector, SelectionKey.OP_ACCEPT);

		} catch (Exception e) {
			LOGGER.error("Problem with starting selector thread", e);
		}

        /**
         * The thread that waits for ready Channels - accept / read
         */
        SelectorThread readThread = new SelectorThread();
        readThread.setDaemon(true);
        readThread.start();
    }

	/**
	 * Stop the selector thread
	 */
	public synchronized void stop() {
		LOGGER.info("Starting shutdown of the mud server");
		try {
			ssch.socket().close();
			ssch.close();
		} catch (Exception e) {
			LOGGER.error("Problem with stopping selector thread", e);
		}
		this.ssch = null;
		LOGGER.info("Finished shutdown of the mud server");
	}

    /**
     * Thread which runs the Selector
     */
    private class SelectorThread extends Thread {

        private final ByteBuffer buffer = ByteBuffer.allocate(1024);

		public SelectorThread() {
			super("SelectorThread");
		}

		private void readSocket(SelectionKey sk) throws IOException {

			SocketChannel sc = (SocketChannel) sk.channel();

			buffer.clear();

			int numberOfBytesRead =sc.read(buffer);

			if (numberOfBytesRead == -1) {
				LOGGER.info("SocketChannel closing after read -1");
				sc.close();
				sk.cancel();
				return;
			}

			buffer.flip();

			StringBuilder sb = new StringBuilder();
			int count2 = buffer.remaining();
			for (int i = 0; i < count2; i++) {
				char c = (char) buffer.get();

				// Consider other filtering!!
				if (c != '\r' && c != '\n') {
					sb.append(c);
				}
			}

			if (sb.length() < 1) {
				return ;
			}

			ConnectionManager.process(sc, sb.toString());

        }

        /**
         * run method
         */
        @Override
		public void run() {
			LOGGER.info("Starting running mud server read sockets");
			try {
				// block until a Channel is ready for I/O
				while (readSelector.select() > 0) {

				    try {
                        Iterator<SelectionKey> selectionKeyIter = readSelector
                                .selectedKeys().iterator();

                        while (selectionKeyIter.hasNext()) {
                            SelectionKey sk = selectionKeyIter.next();
                            selectionKeyIter.remove();
                            if (sk.isAcceptable()) {

                                // new client connection
                                ServerSocketChannel nextReady = (ServerSocketChannel) sk
                                        .channel();

                                SocketChannel channel = nextReady.accept();

                                channel.configureBlocking(false);
                                channel.register(readSelector, SelectionKey.OP_READ);

                                writeSelector.wakeup(); // Added as required by
                                // specification

                                channel.register(writeSelector,
                                        SelectionKey.OP_WRITE);
                                ConnectionManager.add(channel);
                            } else if (sk.isConnectable()) {
                                LOGGER.info("SelectionKey is connected");
                            } else if (sk.isReadable()) {

                                try {
                                    readSocket(sk);
                                } catch (IOException ioe) {
                                    ConnectionManager.close(sk);
                                    throw ioe;
                                }
                            } else if (sk.isWritable()) {
                                LOGGER.info("SelectionKey is writable");
                            } else if (!sk.isValid()) {
                                LOGGER.info("SelectionKey is invalid");
                                new MudException("SelectionKey is invalid:");
                            }
                        }
                    } catch (Throwable t) {
                        LOGGER.error("Problem with communication channel", t);
                    }
//					selectionKeyIter.remove();
				}
			} catch (Exception e) {
				LOGGER.error("Problem with communication channel", e);
			}
		} // end run()

	} // end class

}
