/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.utils.MudArrayList;

import java.util.List;

public class Scan extends BaseCommand {

    private static final String DISTANCE[] = {
            "is pretty close by",
            "is close by",
            "is not far off",
            "is a long way off",
            "is very distant"};

    private Mob myMob;
    private int distance;

    @Override
    public void execute(Mob mob, String input) {

        Scan scanner = new Scan();

        scanner.myMob = mob;

        scanner.distance = DISTANCE.length;

        if (!mob.getRoom().getSectorType().isInside() && World.getWeather().isBlocksScan()) {
            scanner.distance = 1;
            mob.out("The weather is reducing visibility");
        }

        scanner.out("You look all around you...");

        scanner.scan(mob.getRoom().getExits());

        scanner = null; // Probably would not have to do this.

    }

    private void out(String message) {
        myMob.out(message);
    }

    private void scan(Exit exit, int distance) {
        if (distance > distance || exit == null) {
            return;
        }
        showCharacters(exit.getDestinationRoom().getMobs(), distance, exit);
        scan(exit.getDestinationRoom().getExit(exit.getId()), ++distance);
    }

    private void scan(List<Exit> exits) {
        for (Exit exit : exits) {
            if (exit.isScanable()) {
                scan(exit, 0);
            }
        }
    }

    private void showCharacters(MudArrayList<Mob> mobs, int distance, Exit exit) {
        for (Mob mob : mobs) {
            if (myMob.hasDetectHidden() || !mob.isHidden()) {
                if (myMob.hasDetectInvisible() || !mob.isInvisible()) {
                    out(mob.getName() + " " + DISTANCE[distance] + " to the "
                            + exit.getId() + "\n");
                }
            }
        }
    }

}
