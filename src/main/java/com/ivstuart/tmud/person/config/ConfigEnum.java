package com.ivstuart.tmud.person.config;

public enum ConfigEnum {

    ANSI("You will receive ansi color codes"),
    ASSIST("You will automatically assist your group in combat"),
    AUTO_EXIT("You will automatically see exits"),
    AUTO_LOAD("You will automatically split items looted from corpses"),
    AUTO_LOOT("You will automatically loot corpses"),
    AUTO_SAC( "You will automatically sacrifice corpses"),
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


    ConfigEnum(String on) {
        this.on = on;
        this.off = on.replaceFirst("will","will NOT");
    }

    public String getOn() {
        return on;
    }

    public String getOff() {
        return off;
    }

    private final String on;

    private final String off;


}
