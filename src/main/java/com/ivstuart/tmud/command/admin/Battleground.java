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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.config.FightEnum;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.places.RoomLocation;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
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

    public static RoomLocation getRandomStartLocation() {
        int x = DiceRoll.ONE_D_TEN.roll() - 1;
        int y = -1 * (DiceRoll.ONE_D_TEN.roll() - 1);
        int z = 0;
        return new RoomLocation(x,y,z);
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

        if (input != null && !input.isEmpty()) {
            countDown = Integer.parseInt(input);
        }

        // Warn 5 minutes until start of battleground and count down every 10 seconds.
        World.out("A battle ground will start in 5 minutes time");

        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        scheduledExecutorService.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);

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
        if (countDown < 0) {
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

            mob.setRoomLocationToBeforeBattleGround();
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
            if (aMob.getPlayer().getConfig().getFightData().isFlagSet(FightEnum.BATTLEGROUND)) {
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
            RoomLocation startLocation = getRandomStartLocation();
            Room destination = World.getRoom(startLocation);

            if (destination != null) {
                mob.getRoom().remove(mob);
                mob.setRoomLocation(startLocation);
                destination.add(mob);
            } else {
                LOGGER.error("Room id: " + startLocation + " does not exist to battleground in");
            }
        }


    }
}
