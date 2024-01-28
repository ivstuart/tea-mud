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

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Wander extends BaseBehaviour {

    private final static Logger LOGGER = LogManager.getLogger();

    private List<Room> rooms;

    public Wander() {
        parameter = 50;
        parameter2 = 2;
    }

    @Override
    public String getId() {
        return mob.getId();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean tick() {

        if (mob.getFight().isFighting()) {
            LOGGER.debug(mob.getName() + " is fighting and hence will not wander away");
            return false;
        }

        if (mob.getFight().isEngaged()) {
            LOGGER.debug(mob.getName() + " is engaged and hence will not wander away");
            return false;
        }

        if (DiceRoll.ONE_D100.rollMoreThan(parameter)) {
            LOGGER.debug(mob.getName() + " is does not feel like wandering this tick");
            return false;
        }

        // Lazy init
        if (rooms == null) {
            rooms = new ArrayList<>(parameter2);
        }

        if (rooms.size() < parameter2) {

            Exit exit = MoveManager.random(mob);

            if (exit == null) {
                return false;
            }
            LOGGER.debug("Mob wanders to a new location");
        } else {

            int index = rooms.size() - 2;

            MoveManager.move(mob, rooms.get(index));

            LOGGER.debug("Mob wanders to a old location");
        }


        Room room = mob.getRoom();

        int index = rooms.indexOf(room);

        if (index != -1) {
            // A-B-C-D rooms if in D and goes to C then only need to keep list
            // A-B-C
            rooms = rooms.subList(0, index);
        } else {

            rooms.add(room);
        }
        LOGGER.debug("Wandering at a distance of " + rooms.size() + " from source");
        return false;
    }

}
