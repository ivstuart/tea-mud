package com.ivstuart.tmud.command;

import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.misc.NullCommand;
import com.ivstuart.tmud.utils.FileHandle;
import com.ivstuart.tmud.utils.MudHash;

/**
 * Central place to obtain command objects. User or code requests a command
 * object to execute.
 * 
 * @author Ivan
 * 
 */
public class CommandProvider {

	private static final String command_config_path = "/src/main/resources/config/";

	private static final Logger LOGGER = Logger
			.getLogger(CommandProvider.class);

	@SuppressWarnings("unused")
	private static CommandProvider INSTANCE;

	private static final Command NULL_COMMAND = new NullCommand();

	private static MudHash<Command> commandHash = new MudHash<Command>();
	
	private static final String classprefix = "com.ivstuart.tmud.";

	static {
		INSTANCE = new CommandProvider();
	}

	private static Command create(String name) {
		try {
			// TODO classprefix 
			return (Command) Class.forName(classprefix + name).newInstance();
		} catch (Exception e) {
			LOGGER.error("Problem creating new instance", e);
		}

		LOGGER.warn("Command [" + name + "] does not exists");

		return NULL_COMMAND;
	}

	public static Command getCommand(Class<?> aclass) {

		String name = aclass.getSimpleName().toLowerCase();

		return commandHash.get(name);
	}

	/**
	 * @param string
	 */
	public static Command getCommandByString(String string) {
		return commandHash.get(string);

	}

	public static Collection<Command> getCommands() {
		return commandHash.values();
	}

	public static String getLowerCaseLastToken(String msg, String delim) {

		if (delim == null || msg == null) {
			return msg;
		}

		int lastIndexOfDelim = msg.lastIndexOf(delim);

		if (lastIndexOfDelim == -1) {
			return msg.toLowerCase();
		}

		return msg.substring(lastIndexOfDelim + delim.length()).toLowerCase();
	}

	public static boolean reloadCommand(String command) {

		Command replacementCommand = create(command);

		if (NULL_COMMAND == replacementCommand) {
			return false;
		}

		commandHash.replace(getLowerCaseLastToken(command, "."),
				replacementCommand);

		return true;
	}

	private CommandProvider() {

		clearAndLoadCommands();
	}

	public void clearAndLoadCommands() {
		FileHandle file = new FileHandle(System.getProperty("user.dir") + command_config_path + "commands.txt");
			
		try {
			file.read();
		} catch (IOException e) {
			LOGGER.error("Problem reading command.txt file", e);
			return;
		}

		LOGGER.info("Attempting to load commands!");

		commandHash.clear();

		while (file.hasMoreLines()) {
			String line = file.getLine();

			LOGGER.info("Line [" + line + "]");

			if (line != null && line.indexOf(".") > -1 && !line.startsWith("#")) {
				commandHash.add(getLowerCaseLastToken(line, "."), create(line));
			}
		}

		commandHash.setDefault(new NullCommand());
	}
}