package com.ivstuart.tmud.state.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.state.BaseSkill;
import com.ivstuart.tmud.state.BasicThing;
import com.ivstuart.tmud.state.GuardMob;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.state.Zone;
import com.ivstuart.tmud.utils.FileHandle;

public class StateReader {

	private static final Logger LOGGER = Logger.getLogger(StateReader.class);

	private static String INCLUDE = "include";

	private static String CLASS = "class";

	private static String NEW_CLASS = "id";

	private static String CLONE = "clone";

	private static StateReader loader = new StateReader();

	private static String _defaultConfigFile = "index.txt";

	private static String _delim;

	private static String _dir;
	
	private static final String classprefix = "com.ivstuart.tmud.";

	private static void add(Object obj_) {

		if (obj_ instanceof Room) {
			World.add((Room) obj_);
			return;
		}

		if (obj_ instanceof GuardMob) {
			World.add((GuardMob) obj_);
			return;
		}

		if (obj_ instanceof Mob) {
			World.add((Mob) obj_);
			return;
		}

		if (obj_ instanceof Item) {
			World.add((Item) obj_);
			return;
		}

		if (obj_ instanceof Zone) {
			World.add((Zone) obj_);
			return;
		}

		if (obj_ instanceof Spell) {
			World.add((Spell) obj_);
			return;
		}

		if (obj_ instanceof BaseSkill) {
			World.add((BaseSkill) obj_);
			return;
		}

		if (obj_ instanceof Prop) {
			World.add((Prop) obj_);
			return;
		}

	}

	private static Object createClone(Object entity, String id) {
		if (entity instanceof Room) {
			return World.getRoom(id).clone();
		}

		if (entity instanceof GuardMob) {
			return new GuardMob(World.getMob(id));
		}

		if (entity instanceof Mob) {
			return new Mob(World.getMob(id));
		}

		if (entity instanceof Item) {
			return EntityProvider.createItem(id);
		}
		return entity;
	}

	public static StateReader getInstance() {
		return loader;
	}

	// public static void load() gets propertie which points at index file which
	// references other files to load.

	private static Method getMethod(Object object, String name, Class<?> aClass) {
		Method method = null;
		try {
			method = object.getClass().getMethod(name, aClass);
		} catch (NoSuchMethodException e) {
		}
		return method;
	}

	public static void load() {
		try {
			load(_defaultConfigFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void load(String filename_) throws Exception {

		LOGGER.info("Loading from file:" + filename_);

		String filename = _dir + filename_;

		FileHandle file = new FileHandle(filename);

		try {
			file.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object obj = null;
		String currentClass = null;

		while (file.hasMoreLines()) {
			String line = file.getLine();

			int delimPos = line.indexOf(":");

			if (line.startsWith("#") || delimPos == -1) {
				continue;
			}

			String tag = line.substring(0, delimPos);

			String args = "";

			if (delimPos + 2 <= line.length()) {
				args = line.substring(delimPos + 2);

				if (args.indexOf("constants.") > -1) {

					try {
						String[] params = args.split(" ");
						
						LOGGER.debug(params[0] + params[1]);

						// TODO decide if enum is better to model SHIELD_BLOCK
						Field field = Class.forName(classprefix + params[0])
								.getDeclaredField(params[1]);

						Object value = field.get(null);

						args = value.toString();
					} catch (Exception e) {
						LOGGER.warn("Problem calling setter",e);
					}

				}

			}

			LOGGER.debug("tag = " + tag + " args = " + args);

			if (CLASS.equals(tag)) {
				currentClass = args;
				continue;
			}

			if (NEW_CLASS.equals(tag)) {
				if (obj != null) {
					add(obj);
				}
				if (currentClass != null) {
					try {
						LOGGER.info("Creating instance of class type [ "
								+ currentClass + " ]");
						Class<?> theClass = Class.forName(classprefix + currentClass);
						obj = theClass.newInstance();
					} catch (InstantiationException ie) {
						ie.printStackTrace();
						LOGGER.warn("Problem with tag = " + tag + " in class "
								+ currentClass + " for args " + args);
					}
				} else {
					LOGGER.warn("No class tag has been set!");
				}

			}

			if (CLONE.equals(tag)) {
				String id = ((BasicThing) obj).getId();
				obj = createClone(obj, args);
				((BasicThing) obj).setId(id);
				continue;
			}

			if (INCLUDE.equals(tag)) {
				// call recusively to load other files...
				StateReader.load(args);
				continue;
			}

			String methodName = "set" + tag.substring(0, 1).toUpperCase()
					+ tag.substring(1);

			Method method = null;

			// TODO look for method of that required name on the object and back
			// up the class hierachy for params
			// String and Integer. See if this works.

			try {

				// Responsiblity of called method to deal with string to number
				// conversions.
				method = getMethod(obj, methodName, String.class);
				Method integerMethod = getMethod(obj, methodName, int.class);

				if (method != null && integerMethod != null) {
					LOGGER.warn("Tag = " + tag + " in class " + currentClass
							+ " has both string and integer methods "
							+ methodName);
				}

				Object invokeParams = args;

				// If there is no string setter in class then try to get an
				// integer setter.
				if (method == null) {
					if (integerMethod != null) {
						invokeParams = Integer.valueOf(args);
						method = integerMethod;
					}
				}

				if (method == null) {
					LOGGER.warn("Tag = " + tag + " in class " + currentClass
							+ " for args " + args + " not found for method = "
							+ methodName);
				} else {
					method.invoke(obj, invokeParams);
				}

			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.warn("Problem with tag = " + tag + " in class "
						+ currentClass + " for args " + args + " for method = "
						+ methodName);
			}

		}

		add(obj);

	}

	private StateReader() {
		_delim = System.getProperty("file.separator");
		
		// TODO source from properties file (-D option to launch script)
		_dir = System.getProperty("user.dir") + "/src/main/resources/world/";
		//+ _delim + "state" + _delim	+ "world" + _delim;

		LOGGER.debug("Directory location for reading word files [" + _dir + "]");
	}
}
