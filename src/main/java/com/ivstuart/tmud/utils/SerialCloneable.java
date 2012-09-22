/*
 * Created on Oct 27, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ivstuart.tmud.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Ivan Stuart
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SerialCloneable implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object clone() {
		try {
			// Save the object to a byte array
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(this);
			out.close();

			// Read a clone of the object from the byte array
			ByteArrayInputStream bin = new ByteArrayInputStream(
					bout.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object ret = in.readObject();
			in.close();

			return ret;

		} catch (Exception e) {
			return new RuntimeException(e.getMessage());
		}
	}
}
