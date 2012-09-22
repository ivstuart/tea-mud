/*
 * Created on 04-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.utils;

import java.io.*;
import java.util.zip.*;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.CommandProvider;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MudIO {

	private static final Logger LOGGER = Logger
			.getLogger(CommandProvider.class);

//	public static final String saveDir = "saved/";
	
	// TODO source path from mudserver.properties
	public static final String saveDir = "src/main/resources/saved/";

	public static Object load(String fileName) throws Exception {
		FileInputStream aFileInputStream = new FileInputStream(saveDir
				+ fileName);

		fileName = fileName.toLowerCase();

		ObjectInputStream aObjectInputStream = new ObjectInputStream(
				new GZIPInputStream(aFileInputStream));

		Object loadedObject = aObjectInputStream.readObject();

		LOGGER.info("Loaded object from file:" + fileName);

		aObjectInputStream.close();

		return loadedObject;
	}

	public static Object load(String fileName, boolean gzip) throws Exception {

		fileName = fileName.toLowerCase();

		if (gzip) {
			return load(fileName);
		}

		FileInputStream aFileInputStream = new FileInputStream(saveDir
				+ fileName);

		ObjectInputStream aObjectInputStream = new ObjectInputStream(
				aFileInputStream);

		Object loadedObject = aObjectInputStream.readObject();

		LOGGER.info("Loaded object from file:" + fileName);

		aObjectInputStream.close();

		return loadedObject;
	}

	public static void save(Object saveObject, String fileName)
			throws IOException {

		fileName = fileName.toLowerCase();

		ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(
				new FileOutputStream(saveDir + fileName)));

		out.writeObject(saveObject);

		LOGGER.info("Saved object to file:" + fileName);

		out.close();
	}

	public static void save(Object saveObject, String fileName, boolean gzip)
			throws IOException {

		fileName = fileName.toLowerCase();

		if (gzip) {
			save(saveObject, fileName);
			return;
		}

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				saveDir + fileName));

		out.writeObject(saveObject);

		LOGGER.info("Saved object to file:" + fileName);

		out.close();
	}

}
