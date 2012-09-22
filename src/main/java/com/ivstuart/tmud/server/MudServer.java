package com.ivstuart.tmud.server;

/**
 * This example demostrate use of multiple Selectors() with a single SocketChannel.
 * There are two threads running Selectors() each registered for READ and WRITE respectively
 */

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

import org.apache.log4j.Logger;

public class MudServer {

	private static final Logger LOGGER = Logger.getLogger(MudServer.class);

	/** Thread which runs the Selector */
	private class SelectorThread extends Thread {

		private ByteBuffer buffer = ByteBuffer.allocate(1024);

		public SelectorThread() {
			super("SelectorThread");
		}

		private void readSocket(SocketChannel socketChannel) throws IOException {

			buffer.clear();

			int numberOfBytesRead = socketChannel.read(buffer);

			if (numberOfBytesRead <= 0) {
				LOGGER.debug("Less than 1 byte read [" + numberOfBytesRead
						+ "]");
				socketChannel.close();
				return;
			}

			buffer.flip();

			StringBuilder sb = new StringBuilder();
			int count2 = buffer.remaining();
			for (int i = 0; i < count2; i++) {
				char c = (char) buffer.get();

				// TODO think about other filtering!!
				if (c != '\r' && c != '\n') {
					sb.append(c);
				}
			}

			if (sb.length() < 1) {
				return;
			}

			ConnectionManager.process(socketChannel, sb.toString());

		}

		@Override
		public void run() {
			try {
				// block until a Channel is ready for I/O
				while (readSelector.select() > 0) {

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
						} else if (sk.isReadable()) {

							readSocket((SocketChannel) sk.channel());
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("Problem with communication channel", e);
			}
		} // end run()

	} // end class

	/** Single selector for accepts, reads */
	private Selector readSelector = null;

	/** Single selector for writes */
	private Selector writeSelector = null;

	/** ServerSocketChannel which listens for client connections */
	private ServerSocketChannel ssch = null;

	/** The thread that waits for ready Channels - accept / read */
	private SelectorThread readThread = null;

	protected void startListening(String port) {
		startListening(Integer.parseInt(port));
	}

	/**
	 * Sets up the selectors and starts listening
	 */
	protected void startListening(int port) {
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

		this.readThread = new SelectorThread();
		this.readThread.setDaemon(true);
		this.readThread.start();
	}

	/** Stop the selector thread */
	public synchronized void stop() {
		try {
			this.ssch.close();
		} catch (Exception e) {
			LOGGER.error("Problem with stopping selector thread", e);
		}
		this.ssch = null;
	}

}
