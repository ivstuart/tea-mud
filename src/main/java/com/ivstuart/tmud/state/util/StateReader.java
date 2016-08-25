package com.ivstuart.tmud.state.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.ivstuart.tmud.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.utils.FileHandle;

public class StateReader {

	private static final String CLASS = "class";

	private static final String CLONE = "clone";

	private static final String INCLUDE = "include";

	private static StateReader loader = new StateReader();

	private static final Logger LOGGER = LogManager.getLogger();

	private static final String NEW_OBJECT_ID = "id";

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

		if (entity instanceof GuardMob) {
			return new GuardMob(World.getMob(id));
		}

		if (entity instanceof ShopKeeper) {
			return new ShopKeeper(World.getMob(id));
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

		// TODO check for World.getObject
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

					// TODO decide if enum is better to model SHIELD_BLOCK
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

			Method method = getMethod(obj, methodName, String.class);

			Object invokeParams = args;

			if (method == null) {
				method = getMethod(obj, methodName, int.class);

				if(method == null) {
					LOGGER.warn("Class does not have a setter method with name "+methodName+" for param int or string");
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

			// TODO comment out to speed up launch time.
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
				// TODO check if really new world id if not pull object from World. World.getObject(args);
				obj = createOrAddNewObject(obj, currentClass, args);
			}

			invokeMethod(obj, currentClass, tag, args);

		}

		addToWorld(obj);
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
			LOGGER.debug("Problem loading state file", e);
		}

		load(file);

	}

}
