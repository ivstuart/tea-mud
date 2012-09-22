/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.utils;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StringPair {

	private String first;

	private String second;

	/**
	 * 
	 */
	public StringPair(String source, String target) {
		super();
		first = source;
		second = target;
	}

	public String getSource() {
		return first;
	}

	public String getTarget() {
		return second;
	}

}
