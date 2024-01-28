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

package com.ivstuart.tmud.world;

import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.state.DeadMob;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.util.EntityProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class WorldTime implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final WorldTime INSTANCE = new WorldTime();
    private static final boolean _running = false;
    private static List<Mob> fighting;
    private static List<DeadMob> deadMobs;
    private static List<Tickable> tickables;
    private static boolean pauseTime = false;
    private final int tickSpeed = 150;
    private int counter = 0;

    private WorldTime() {
        WorldTime.init();
    }

    public static List<Tickable> getTickables() {
        return tickables;
    }

    public static void setTickables(List<Tickable> tickables) {
        WorldTime.tickables = tickables;
    }

    public static void addFighting(Mob mob_) {
        synchronized (fighting) {
            if (fighting.contains(mob_)) {
                LOGGER.debug("Mob already fighting no need to add");
                return;
            }
            fighting.add(mob_);
        }
    }

    public static void addTickable(Tickable item_) {
        if (tickables.contains(item_)) {
            return;
        }
        tickables.add(item_);
    }

    /**
     * @return
     */
    public static List<Mob> getFighting() {
        return fighting;
    }

    /**
     * Only World and TestHelper are allowed to get an instance of WorldTime
     *
     * @return
     */
    public static WorldTime getInstance() {
        return INSTANCE;
    }

    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        return "Time is : " + calendar.getTime();
    }

    public static void init() {

        fighting = new ArrayList<>();
        tickables = new ArrayList<>();
        deadMobs = new ArrayList<>();
    }

    public static boolean removeItem(Item item_) {
        return tickables.remove(item_);
    }

    public static void scheduleMobForRepopulation(Mob mob_) {
        LOGGER.debug("scheduleMobForRepopulation with id [ " + mob_.getId()
                + " ]");

        DeadMob dead = new DeadMob(mob_.getId(), mob_.getRepopRoomId(), 10);

        deadMobs.add(dead);
    }

    public static boolean isPauseTime() {
        return pauseTime;
    }

    public static boolean togglePauseTime() {
        pauseTime = !pauseTime;
        return pauseTime;
    }

    public static boolean removeTickables(Mob mob) {
        return tickables.remove(mob);
    }

    public void repopulateMobs() {
        repopulateMobs(false);
    }

    public void repopulateMobs(boolean forceRepop) {
        for (Iterator<DeadMob> iter = deadMobs.iterator(); iter.hasNext(); ) {
            DeadMob deadMob = iter.next();
            if (deadMob.shouldRepopulate() || forceRepop) {

                iter.remove();
                Mob mob = EntityProvider.createMob(deadMob.getID(),
                        deadMob.getRepopRoomID());

                Room repopRoom = World.getRoom(deadMob.getRepopRoomID());

                if (repopRoom == null) {
                    LOGGER.error("Repopulate room is null");
                    continue;
                }

                repopRoom.add(mob);
            }
        }
    }

    /**
     * Resolve combat and remove from combat any that are not fighting
     */
    public void resolveCombat() {
        synchronized (fighting) {
            if (fighting.isEmpty()) {
                // LOGGER.debug("Fighting is empty so no combat to resolve");
                return;
            }

            // Iterator would throw a ConcurrentModification Exception
            for (int index = 0; index < fighting.size(); index++) {
                Mob mob = fighting.get(index);
                if (mob != null) {
                    Fight fight = mob.getFight();

                    // LOGGER.debug("Is fighting " + fight.isFighting() + " has fight actions " + fight.hasFightActions());

                    if (fight.isFighting() || fight.hasFightActions()) {
                        try {
                            fight.resolveCombat();
                        } catch (RuntimeException re) {
                            LOGGER.error("Problem in resolveCombat", re);
                        }
                    } else {
                        LOGGER.debug("removing from fighting [ " + mob.getName()
                                + " ]");

                        fighting.remove(mob);
                    }
                }

            }
        }
    }

    @Override
    public void run() {
        try {

            counter = ++counter % tickSpeed;
            if (counter == 1) {

                sendHeartBeat();
                repopulateMobs();
            }

            resolveCombat();
        } catch (Throwable t) {
            LOGGER.error("Problem in fighting thread", t);
        }
    }

    public void sendHeartBeat() {

        if (pauseTime) {
            return;
        }

        for (int index = 0; index < tickables.size(); index++) {
            if (tickables.get(index).tick()) {
                tickables.remove(index);
            }

        }

    }

    public void tickWithCombat() {
        sendHeartBeat();
        repopulateMobs();
        resolveCombat();
    }
}
