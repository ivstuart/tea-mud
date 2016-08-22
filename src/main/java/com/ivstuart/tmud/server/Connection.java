/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.server;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.io.*;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author stuarti
 * 
 */
public class Connection {

	private static final Logger LOGGER = LogManager.getLogger();

	private static Charset charset = Charset.forName("ISO-8859-1");

	private static CharsetEncoder encoder = charset.newEncoder();

	private SocketChannel sc;

	private Readable state;

	private CharBuffer cbuf;

	public Connection() {

	}

	/**
	 * 
	 */
	public Connection(SocketChannel aSocketChannel) {

		LOGGER.info("Connection created for [ " + aSocketChannel + " ]");

		sc = aSocketChannel;

		state = new Login(this);

	}

	public void disconnect() {

		LOGGER.info("Disconnecting socket channel [ " + sc + " ]");

		try {
			sc.socket().close();
			sc.close();
			// ConnectionManager.close(sk);

		} catch (IOException e) {
			LOGGER.error("Problem closing connection", e);
		}
	}

	public Readable getState() {
		return state;
	}

	public boolean isConnected() {
		return sc.isConnected();
	}

	public void out(String output) {

		cbuf = CharBuffer.wrap(output + "\n");

		try {
			sc.write(encoder.encode(cbuf));
		} catch (IOException e) {

			LOGGER.error("Problem writing to connection", e);
		}

	}

	public void process(String cmd) {
		state.read(cmd);
	}

	public void setState(Readable readable) {

		LOGGER.info("Setting state  [ " + readable + " ]");

		state = readable;
	}

}
