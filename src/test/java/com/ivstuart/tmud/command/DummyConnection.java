/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command;



import com.ivstuart.tmud.server.Connection;
import com.ivstuart.tmud.server.Readable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DummyConnection extends Connection {

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * 
	 */
	public DummyConnection() {

	}

	@Override
	public void disconnect() {
		LOGGER.debug("disconnect");
	}

	@Override
	public Readable getState() {
		LOGGER.debug("get state");
		return null;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void out(String output) {

		LOGGER.debug("output [ " + output + " ]");

	}

	@Override
	public void process(String cmd) {
		LOGGER.debug("command [ " + cmd + " ]");
	}

	@Override
	public void setState(Readable readable) {
		LOGGER.debug("set state");
	}

}
