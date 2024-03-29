/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.command;

import com.ivstuart.tmud.command.misc.NullCommand;
import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.utils.FileHandle;
import com.ivstuart.tmud.utils.MudHash;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collection;

/**
 * Central place to obtain command objects. User or code requests a command
 * object to execute.
 *
 * @author Ivan
 */
public class CommandProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Command NULL_COMMAND = new NullCommand();
    @SuppressWarnings("unused")
    private static final CommandProvider INSTANCE = new CommandProvider();
    private static MudHash<Command> commandHash;

    private CommandProvider() {

        commandHash = new MudHash<>();

        clearAndLoadCommands();

        loadSocials();
    }

    private static Command create(String name) {
        try {
            String classprefix = LaunchMud.getMudServerClassPrefix();
            return (Command) Class.forName(classprefix + name).newInstance();
        } catch (Exception e) {
            LOGGER.error("Problem creating new instance", e);
        }

        LOGGER.warn("Command [" + name + "] does not exists");

        return NULL_COMMAND;
    }

    public static Command getCommand(Class<?> aclass) {

        String name = aclass.getSimpleName().toLowerCase();

        Command command = commandHash.get(name);

//		if (command == null) {
//			LOGGER.warn("Null command hence creating it on the fly");
//			command = create(aclass.getCanonicalName());
//		}

        return command;
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

    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    public void clearAndLoadCommands() {
        String command_config_path = LaunchMud.getMudServerConfigDir();
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

            LOGGER.debug("Line [" + line + "]");

            if (line != null && line.contains(".") && !line.startsWith("#")) {

                String positionParam = null;

                if (line.contains(",")) {
                    String[] lines = line.split(",", 2);
                    line = lines[0];
                    positionParam = lines[1];
                }
                Command command = create(line);

                if (positionParam != null) {
                    command.setMinimumPosition(MobState.valueOf(positionParam));
                }

                commandHash.add(getLowerCaseLastToken(line, "."), command);
            }
        }

        commandHash.setDefault(new NullCommand());
    }

    public void loadSocials() {
        String command_config_path = LaunchMud.getMudServerConfigDir();
        FileHandle file = new FileHandle(System.getProperty("user.dir") + command_config_path + "socials.txt");

        try {
            file.read();
        } catch (IOException e) {
            LOGGER.error("Problem reading socials.txt file", e);
            return;
        }

        LOGGER.info("Attempting to load social commands!");

        while (file.hasMoreLines()) {
            String line = file.getLine().trim();

            LOGGER.debug("Line [" + line + "]");

            String positionParam = null;
            String description = null;

            if (line.contains(",")) {
                String[] lines = line.split(",", 3);
                line = lines[0];
                positionParam = lines[1];
                if (lines.length > 2) {
                    description = lines[2];
                }
            }
            Command command = createSocial(line);

            if (positionParam != null) {
                command.setMinimumPosition(MobState.valueOf(positionParam));
            }

            if (description != null) {
                ((Social) command).setDescription(description);
            }

            commandHash.add(getLowerCaseLastToken(line, "."), command);

            if (line != null && !line.startsWith("#")) {
                commandHash.add(line, command);
            }
        }

    }

    private Command createSocial(String line) {
        return new Social(line);
    }

}