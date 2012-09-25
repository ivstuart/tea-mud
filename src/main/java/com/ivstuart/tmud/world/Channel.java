/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.world;

import java.util.LinkedList;
import java.util.List;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Channel {

	private static final int MAX_SIZE = 20;

	private List<String> good;

	// TODO
	private List<String> evil;

	/**
	 * 
	 */
	public Channel() {
		super();
		good = new LinkedList<String>();
		evil = new LinkedList<String>();
	}

	public void add(String message) {
		good.add(message);

		if (good.size() > MAX_SIZE) {
			good.remove(0);
		}
	}

	public String toString(int channel) {
		StringBuilder sb = new StringBuilder();

		for (String lineOfChat : good) {
			sb.append(lineOfChat).append("\n");
		}

		return sb.toString();
	}
}
