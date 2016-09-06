/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command;

import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 14/08/2016.
 */
public class Social extends BaseCommand {

    private final String cmd;
    private String description;

    public Social(String line) {
        this.cmd = line;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    @Override
    public void execute(Mob mob, String input) {
        mob.out("You "+cmd+"'s "+description);
    }

    @Override
    public String getHelp() {
        return "The command " + cmd + " is a social commands for communication only";
    }
}
