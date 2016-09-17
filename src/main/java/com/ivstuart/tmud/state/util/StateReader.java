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

package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.utils.FileHandle;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StateReader {

	private static final String CLASS = "class";

	private static final String CLONE = "clone";

	private static final String INCLUDE = "include";
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String NEW_OBJECT_ID = "id";
	private static StateReader loader = new StateReader();

	public static StateReader getInstance() {
		return loader;
	}

	private void addToWorld(Object object) {
		World.getInstance().addToWorld(object);
	}

	private Object createClone(Object entity, String id) {


		if (entity instanceof Room) {
			return World.getRoom(id).clone();
		}

		// Note use reflection for Mobs
		if (entity instanceof GuardMob) {
			return new GuardMob((GuardMob) World.getMob(id));
		}

		if (entity instanceof ShopKeeper) {
			return new ShopKeeper((ShopKeeper) World.getMob(id));
		}

		if (entity instanceof GuildMaster) {
			return new GuildMaster((GuildMaster) World.getMob(id));
		}

		if (entity instanceof WarMaster) {
			return new WarMaster((WarMaster) World.getMob(id));
		}

		if (entity instanceof Armourer) {
			return new Armourer((Armourer) World.getMob(id));
		}

		if (entity instanceof Mob) {
			return new Mob(World.getMob(id));
		}

		if (entity instanceof Item) {
			return EntityProvider.createItem(id);
		}
		return entity;
	}

	public Object createOrAddNewObject(Object obj, String currentClass,
			String args) throws ClassNotFoundException, IllegalAccessException {

		if (obj != null) {
			addToWorld(obj);
		}

		if (currentClass.equals("state.Room")) {
			Room room = World.getRoom(args);
			if (room != null) {
				LOGGER.info("Editing an existing object again:" + args);
				return room;
			}
		}

		if (currentClass != null) {
			try {
				LOGGER.info("Creating instance of class type [ " + currentClass
						+ " ]");
				Class<?> theClass = Class.forName(getClassPrefix()
						+ currentClass);
				obj = theClass.newInstance();
			} catch (InstantiationException ie) {
				LOGGER.warn("Problem creating object for class [ "
						+ currentClass + " ] with args [ " + args + " ]", ie);
			}
		} else {
			LOGGER.warn("No class tag has been set!");
		}
		return obj;
	}

	public String getArgs(String line, int delimPos) {
		String args = "";

		if (delimPos + 2 <= line.length()) {
			args = line.substring(delimPos + 2);

			if (args.indexOf("constants.") > -1) {

				try {
					String[] params = args.split(" ");

					LOGGER.debug(params[0] + params[1]);

					Field field = Class.forName(getClassPrefix() + params[0])
							.getDeclaredField(params[1]);

					Object value = field.get(null);

					args = value.toString();
				} catch (Exception e) {
					LOGGER.warn("Problem calling setter", e);
				}

			}

		}
		return args;
	}

	public String getClassPrefix() {
		return LaunchMud.mudServerProperties.getProperty("class.prefix");
	}

	private Method getMethod(Object object, String name, Class<?> aClass) {
		Method method = null;
		try {
			method = object.getClass().getMethod(name, aClass);
		} catch (NoSuchMethodException e) {
		}
		return method;
	}

	public String getMethodName(String tag) {
		return "set" + tag.substring(0, 1).toUpperCase() + tag.substring(1);
	}

	public String invokeMethod(Object obj, String currentClass, String tag,
			String args) {

		String methodName = getMethodName(tag);

		try {
			Method method = null;

			Object invokeParams = args;

			// New in 2016 introduced with race class.
			if (args.equals("true") || args.equals("false")) {
				method = getMethod(obj, methodName, boolean.class);

				invokeParams = Boolean.valueOf(args);
			} else {

				method = getMethod(obj, methodName, String.class);
			}

			if (method == null) {
				method = getMethod(obj, methodName, int.class);

				if(method == null) {
					LOGGER.warn("Class does not have a setter method with name " + methodName + " for param boolean, int or string");
					return methodName;
				}

				invokeParams = Integer.valueOf(args);
			}

			if (method == null) {
				LOGGER.warn("Tag [ " + tag + " ] in class [ " + currentClass
						+ " ]  args [ " + args + " not found for method [ "
						+ methodName + " ]");
			} else {
				method.invoke(obj, invokeParams);
			}

		} catch (Exception e) {
			LOGGER.warn("Problem loading state file", e);

		}
		return methodName;
	}

	public void load() {

		try {
			load(LaunchMud.mudServerProperties
					.getProperty("world.state.filepath"));
		} catch (Exception e) {
			LOGGER.debug("Problem loading state file", e);
		}
	}

	public void load(FileHandle file) throws Exception, ClassNotFoundException,
			IllegalAccessException {
		Object obj = null;
		String currentClass = null;

		while (file.hasMoreLines()) {
			String line = file.getLine();

			if (line.startsWith("#")) {
				continue;
			}

			int delimPos = line.indexOf(":");

			if (delimPos == -1) {
				continue;
			}

			String tag = line.substring(0, delimPos);

			String args = getArgs(line, delimPos);

			// Note comment out to speed up launch time not really a to do.
			// LOGGER.debug("tag [ " + tag + " ] args [ " + args + " ]");

			if (CLASS.equals(tag)) {
				currentClass = args;
				continue;
			}

			if (CLONE.equals(tag)) {
				String id = ((BasicThing) obj).getId();
				obj = createClone(obj, args);
				((BasicThing) obj).setId(id);
				continue;
			}

			if (INCLUDE.equals(tag)) {
				// call recursively to load other files...
				load(args);
				continue;
			}

			if (NEW_OBJECT_ID.equals(tag)) {
				obj = createOrAddNewObject(obj, currentClass, args);
			}

			invokeMethod(obj, currentClass, tag, args);

		}

		if (obj != null) {
			addToWorld(obj);
		} else {
			LOGGER.debug("File " + file.getName() + " has no objects to create");
		}


	}

	public void load(String fileName) throws Exception {

		LOGGER.info("Loading from file [ " + fileName + " ]");

		String filename = System.getProperty("user.dir")
				+ LaunchMud.mudServerProperties.getProperty("world.state.dir")
				+ fileName;

		FileHandle file = new FileHandle(filename);

		try {
			file.read();
		} catch (IOException e) {
			LOGGER.error("Problem loading state file", e);
			return;
		}

		load(file);

	}

}
