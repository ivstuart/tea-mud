package com.ivstuart.tmud.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivstuart.tmud.person.Player;

public class GsonIO {

	private static final Logger LOGGER = Logger.getLogger(GsonIO.class);

	public void save(Object src, String fileName) throws IOException {

		LOGGER.info("Started saving object to file:" + getFullPath(fileName));

		// Gson gson = new Gson();
		GsonBuilder gb = new GsonBuilder();
		gb.setPrettyPrinting();
		// .excludeFieldsWithModifiers(Modifier.TRANSIENT);
		Gson gson = gb.create();

		String output = gson.toJson(src);

		LOGGER.debug("encoding:" + output);

		File file = new File(getFullPath(fileName));
		// creates the file

		file.createNewFile();

		// creates a FileWriter Object
		FileWriter writer = new FileWriter(file);

		// Writes the content to the file
		writer.write(output);
		writer.flush();
		writer.close();

		LOGGER.info("Finnished saving object to file:" + getFullPath(fileName));
	}

	public Player loadPlayer(String fileName) throws IOException {

		LOGGER.info("Starting loading object fromm file:"
				+ getFullPath(fileName));

		String readLine = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getFullPath(fileName)));
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
		} catch (IOException e) {
			LOGGER.error("Error: ", e);
			throw e;
		}

		br.close();
		String json = sb.toString();

		Gson gson = new Gson();
		Player player = gson.fromJson(json, Player.class);

		LOGGER.info("Finnished loading object fromm file:"
				+ getFullPath(fileName));

		return player;
	}

	public String getSaveDirectory() {
		return "src/main/resources/saved/gson/";
		// return LaunchMud.mudServerProperties.getProperty("player.save.dir");
	}

	public String getFullPath(String fileName) {
		return getSaveDirectory() + fileName + ".gson";
	}

	public Object load(String fullPath,Class aClass) throws IOException {
		LOGGER.info("Starting loading object fromm file:"
				+ fullPath);

		String readLine = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fullPath));
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
		} catch (IOException e) {
			LOGGER.error("Error: ", e);
			throw e;
		}

		br.close();
		String json = sb.toString();

		Gson gson = new Gson();
		
		Object object = gson.fromJson(json, aClass);

		LOGGER.info("Finnished loading object fromm file:"
				+ fullPath);

		return object;
		
	}
}
