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

package com.ivstuart.tmud.person.config;

import java.io.Serializable;
import java.util.BitSet;

/*
 * This class is simple a data class to store the configuration
 * for the players character
 */
public class ConfigData implements Serializable {

    public static final int ANSI = 0;
    public static final int ASSIST = 1;
    public static final int AUTOEXIT = 2;
    public static final int AUTOLOAD = 3;
    public static final int AUTOLOOT = 4;
    public static final int AUTOSAC = 5;
    public static final int AUTOSPLIT = 6;
    public static final int BATTLE = 7;
    public static final int BLANK = 8;
    public static final int CHALLENGE = 9;
    public static final int COMBINE = 10;
    public static final int DUALMANA = 11;
    public static final int EQLIST = 12;
    public static final int GROUPSPAM = 13;
    public static final int LEVEL = 14;
    public static final int PIGSPAM = 15;
    public static final int PROMPT = 16;
    public static final int SUMMON = 17;
    public static final int VERBOSE = 18;
    private static final long serialVersionUID = 8405127163570001754L;
    private static final String[] FLAG_NAME = {"ANSI", "ASSIST", "AUTOEXIT",
            "AUTOLOAD", "AUTOLOOT", "AUTOSAC", "AUTOSPLIT", "BATTLE", "BLANK",
            "CHALLENGE", "COMBINE", "DUALMANA", "EQLIST", "GROUPSPAM", "LEVEL",
            "PIGSPAM", "PROMPT", "SUMMON", "VERBOSE"};

    private static final String[] TRUE_DESCRIPTION = {
            "You receive ansi color codes",
            "You automatically assist your group in combat",
            "You automatically see exits",
            "You automatically split items looted from corpses",
            "You automatically loot corpses",
            "You automatically sacrifice corpses",
            "You automatically split coins looted from corpses",
            "You will see progress of battle ground",
            "You have a blank line before your prompt",
            "You allow arena challenges from other players",
            "You see object lists in combined format",
            "You see mana of both gems in your prompt",
            "Equipment is sorted from head to toe",
            "You see group follow spam",
            "Who will display your level",
            "You will see updated status of the Pig Game",
            "Prompt is shown",
            "You can be summoned",
            "You will see room descriptions in verbose"};

    private static final String[] FALSE_DESCRIPTION = {
            "You will not receive ansi color codes",
            "You will not assist your group in combat",
            "You will not see exits",
            "You will not split items looted from corpses",
            "You will not loot corpses",
            "You will not sacrifice corpses",
            "You will not split coins looted from corpses",
            "You will not see progress of battle ground",
            "You will not have a blank line before your prompt",
            "You disallow arena challenges from other players",
            "You see object lists in full",
            "You see mana of primary gem only in your prompt",
            "Equipment is sorted in classic style",
            "You will not see group follow spam",
            "Who will hide your level",
            "You will not see updated status of the Pig Game",
            "Prompt is not shown",
            "You can not be summoned",
            "You see brief descriptions of rooms"};

    private final BitSet flags = new BitSet(FLAG_NAME.length);

    public ConfigData() {
        flags.set(0,FLAG_NAME.length-1,true);
    }

    public boolean is(int index) {
        return flags.get(index);
    }

    public void set(int index, boolean value) {
        flags.set(index,value);
    }

    public void toggle(int index) {
        flags.flip(index);
    }

    public String toggle(String flagName) {
        for (int index = 0; index < FLAG_NAME.length; index++) {
            if (FLAG_NAME[index].equalsIgnoreCase(flagName)) {
                this.toggle(index);
                return toString(index);
            }
        }
        return "No such configuration option" + flagName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < FLAG_NAME.length; index++) {
            sb.append(String.format("[ %1$10s ] %2$s\n", FLAG_NAME[index],
                    this.toString(index)));
        }
        return sb.toString();
    }

    private String toString(int index) {
        if (flags.get(index)) {
            return TRUE_DESCRIPTION[index];
        }
        return FALSE_DESCRIPTION[index];
    }
}
