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

package com.ivstuart.tmud.person.config;

public enum ConfigEnum {

    ANSI("You will receive ansi color codes"),
    ASSIST("You will automatically assist your group in combat"),
    AUTO_EXIT("You will automatically see exits"),
    AUTO_LOAD("You will automatically split items looted from corpses"),
    AUTO_LOOT("You will automatically loot corpses"),
    AUTO_SAC("You will automatically sacrifice corpses"),
    AUTO_SPLIT("You will automatically split coins looted from corpses"),
    BATTLE("You will see progress of battle ground"),
    BLANK("You will have a blank line before your prompt"),
    CHALLENGE("You will allow arena challenges from other players"),
    COMBINE("You will see object lists in combined format"),
    DUAL_MANA("You will see mana of both gems in your prompt"),
    EQUIP_LIST("Equipment will be sorted from head to toe"),
    GROUP_SPAM("You will see group follow spam"),
    LEVEL("Who will display your level"),
    PIG_SPAM("You will see updated status of the Pig Game"),
    PROMPT("Prompt will be shown"),
    SUMMON("You will be summoned"),
    VERBOSE("You will see room descriptions in verbose");


    private final String on;
    private final String off;

    ConfigEnum(String on) {
        this.on = on;
        this.off = on.replaceFirst("will", "will NOT");
    }

    public String getOn() {
        return on;
    }

    public String getOff() {
        return off;
    }


}
