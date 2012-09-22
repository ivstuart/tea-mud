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

public class MudServer {

	/** Thread which runs the Selector */
	private class SelectorThread extends Thread {

		private ByteBuffer buffer = ByteBuffer.allocate(1024);

		public SelectorThread() {
			super("SelectorThread");
		}

		private void readSocket(SocketChannel socketChannel) throws IOException {

			buffer.clear();

			int count = socketChannel.read(buffer);

			if (count <= 0) {
				System.out.println("Count = " + count);
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
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Exception in selector loop: "
						+ ex.toString());
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

	/**
	 * Sets up the selectors and starts listening
	 */
	protected void startListening(int port) {
		try {
			// create the selector and the ServerSocket
			readSelector = SelectorProvider.provider().openSelector();
			writeSelector = SelectorProvider.provider().openSelector();
			ssch = ServerSocketChannel.open();
			ssch.configureBlocking(false);
			InetSocketAddress isa = new InetSocketAddress(
					InetAddress.getLocalHost(), port);
			System.out.println("Started on host = "
					+ InetAddress.getLocalHost());
			ssch.socket().bind(isa);
			ssch.register(readSelector, SelectionKey.OP_ACCEPT);
			ssch.register(writeSelector, SelectionKey.OP_ACCEPT);

			System.out.println("Started listening on port = " + port);
		} catch (Exception e) {
			System.out.println("Error starting listening" + e.toString());
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
			System.err.println("Exception in stop()" + e.toString());
		}
		this.ssch = null;
	}

}
