/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.server.LaunchMud;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;

public class GsonIO {

	private static final Logger LOGGER = LogManager.getLogger();

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

		LOGGER.debug("File path:" + file.getAbsolutePath());

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
		return LaunchMud.mudServerProperties.getProperty("player.ban.dir");
	}

	public String getFullPath(String fileName) {
		return getSaveDirectory() + fileName + ".gson";
	}

	public Object load(String fileName,Class aClass) throws IOException {

		LOGGER.info("Starting loading object fromm file:"
				+ getFullPath(fileName));

		String readLine = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			FileReader fileReader = new FileReader(getFullPath(fileName));
			br = new BufferedReader(fileReader);
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

		LOGGER.info("Finished loading object fromm file:"
				+ getFullPath(fileName));

		return object;

	}

	public Object load(String fileName, Type type) throws IOException {

		LOGGER.info("Starting loading object fromm file:"
				+ getFullPath(fileName));

		String readLine = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			FileReader fileReader = new FileReader(getFullPath(fileName));
			br = new BufferedReader(fileReader);
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

		Object object = gson.fromJson(json, type);

		LOGGER.info("Finished loading object fromm file:"
				+ getFullPath(fileName));

		return object;

	}
}
