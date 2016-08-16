package com.ivstuart.tmud.command;

import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 14/08/2016.
 */
public class Social extends BaseCommand {

    private final String cmd;

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    private String description;

    public Social(String line) {
        this.cmd = line;
    }

    @Override
    public void execute(Mob mob, String input) {
        mob.out("You "+cmd+"'s "+description);
    }
}