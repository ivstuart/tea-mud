/*
 * Created on 04-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.server.LaunchMud;

/**
 * @author stuarti
 */
public class MudIO {

	private static final Logger LOGGER = Logger.getLogger(MudIO.class);
	
	private static final MudIO INSTANCE = new MudIO();

	public Object load(String fileName) throws Exception {
		FileInputStream aFileInputStream = new FileInputStream(
				getSaveDirectory() + fileName);

		fileName = fileName.toLowerCase();

		ObjectInputStream aObjectInputStream = new ObjectInputStream(
				new GZIPInputStream(aFileInputStream));

		Object loadedObject = aObjectInputStream.readObject();

		LOGGER.info("Loaded object from file:" + fileName);

		aObjectInputStream.close();

		return loadedObject;
	}

	public Object load(String fileName, boolean gzip) throws Exception {

		fileName = fileName.toLowerCase();

		if (gzip) {
			return load(fileName);
		}

		FileInputStream aFileInputStream = new FileInputStream(
				getSaveDirectory() + fileName);

		ObjectInputStream aObjectInputStream = new ObjectInputStream(
				aFileInputStream);

		Object loadedObject = aObjectInputStream.readObject();

		LOGGER.info("Loaded object from file:" + fileName);

		aObjectInputStream.close();

		return loadedObject;
	}

	public void save(Object saveObject, String fileName) throws IOException {

		fileName = fileName.toLowerCase();

		ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(
				new FileOutputStream(getSaveDirectory() + fileName)));

		out.writeObject(saveObject);

		LOGGER.info("Saved object to file:" + fileName);

		out.close();
	}

	public void save(Object saveObject, String fileName, boolean gzip)
			throws IOException {

		fileName = fileName.toLowerCase();

		if (gzip) {
			save(saveObject, fileName);
			return;
		}

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				getSaveDirectory() + fileName));

		out.writeObject(saveObject);

		LOGGER.info("Saved object to file:" + fileName);

		out.close();
	}

	public String getSaveDirectory() {
		return LaunchMud.mudServerProperties.getProperty("player.save.dir");

	}

	public static MudIO getInstance() {
		return INSTANCE;
	}

}
