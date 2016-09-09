/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.config.FightData;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Battleground extends AdminCommand implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();
    private static List<Mob> players;
    private static int goodCounter;
    private static int evilCounter;
    private int countDown;
    private ScheduledExecutorService scheduledExecutorService;

    public static String getRandomBattleGroundRoom() {

        int x = DiceRoll.ONE_D_TEN.roll() - 1;
        int y = -1 * (DiceRoll.ONE_D_TEN.roll() - 1);
        int z = 0;

        return "ZBG-:" + x + ":" + y + ":" + z;
    }

    public static void deathOf(Mob defender) {

        if (defender.isGood()) {
            goodCounter--;
        } else {
            evilCounter--;
        }

        World.out("Player " + defender.getName() + " has been killed in the battle ground");


    }

    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        if (scheduledExecutorService != null
                && !scheduledExecutorService.isTerminated()
                && countDown < 1) {
            mob.out("Battle ground still in progress please wait before starting a new one");
            return;
        }

        countDown = 30;

        if (input.length() > 0) {
            countDown = Integer.parseInt(input);
        }

        // Warn 5 minutes until start of battle ground and count down every 10 seconds.
        World.out("A battle ground will start in 5 minutes time");

        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);

    }

    @Override
    public void run() {
        countDown--;

        if (countDown > 0) {
            World.out(countDown + " - A battle ground will start in soon");
        }

        if (countDown == 0) {
            // scheduledExecutorService.shutdown();
            World.out("Starting battle ground");
            startBattleground();
        }
        if (countDown < 0 || countDown < -60) {
            if (checkIfBattlegroundComplete()) {
                scheduledExecutorService.shutdown();
                returnPlayersAndRestoreThem();
            }
        }
    }

    private void returnPlayersAndRestoreThem() {
        for (Mob mob : players) {
            mob.getHp().restore();
            mob.getMv().restore();
            mob.getMana().restore();

            Room destination = World.getRoom(mob.getReturnRoom());

            mob.getRoom().remove(mob);
            destination.add(mob);
            mob.setRoom(destination);
        }
    }

    private boolean checkIfBattlegroundComplete() {
        boolean isOver = false;
        if (goodCounter == 0) {
            World.out("Evil has won the battle ground");
            isOver = true;
        }
        if (evilCounter == 0) {
            World.out("Good has won the battle ground");
            isOver = true;
        }
        return isOver;

    }

    private void startBattleground() {

        players = new ArrayList<>();
        goodCounter = 0;
        evilCounter = 0;

        // Get all players who have fight data set for BATTLEGROUND and teleport them to random location in battleground.
        for (String playerName : World.getPlayers()) {
            Mob aMob = World.getMob(playerName);
            if (aMob.getPlayer().getConfig().getFightData().is(FightData.BATTLEGROUND)) {
                players.add(aMob);

                if (aMob.isGood()) {
                    goodCounter++;
                } else {
                    evilCounter++;
                }

            }

        }

        World.out("Number of good players for battle ground = " + goodCounter);
        World.out("Number of evil players for battle ground = " + evilCounter);

        if (goodCounter == 0 || evilCounter == 0) {
            World.out("Not enough participants to have battle ground");
            return;
        }

        teleportPlayersToBattleground(players);


    }

    private void teleportPlayersToBattleground(List<Mob> mobs) {
        for (Mob mob : mobs) {

            mob.setReturnRoom();
            String roomId = getRandomBattleGroundRoom();
            Room destination = World.getRoom(roomId);

            if (destination != null) {
                mob.getRoom().remove(mob);
                mob.setRoom(destination);
                destination.add(mob);
            } else {
                LOGGER.error("Room id: " + roomId + " does not exist to battleground in");
            }
        }


    }
}
