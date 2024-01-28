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

package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

import static com.ivstuart.tmud.common.MobState.STAND;
import static com.ivstuart.tmud.constants.SkillNames.CLIMBING;

public class EnterNoLook extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(Mob mob, String input) {

        Room room = mob.getRoom();

        if (mob.getFight().isFighting()) {
            mob.out("You can not move while your fighting! Type flee or disengage to exit from combat");
            return;
        }


        if (mob.getFight().isEngaged()) {
            mob.out("You can not move while being engaged in combat!");
            return;
        }

        if (mob.getMobStatus().isHidden()) {
            mob.out("You can not move while hiding!");
            return;
        }

        if (mob.getMv() != null && mob.getMv().getValue() < 0) {
            mob.out("You can not move you are out of movement and too tired!");
            return;
        }

        if (mob.getState().stuck()) {
            mob.out("You can not move you are " + mob.getState().getDesc());
            return;
        }


        Exit exit = room.getExit(input);

        if (exit == null) {
            mob.out("No visible exit in direction " + input + ".");
            return;
        }

        Door door = exit.getDoor();

        if (door != null && door.getState() != DoorState.OPEN && door.getState() != DoorState.BROKEN) {
            mob.out("The exit in direction " + exit.getId() + " is not open.");
            return;
        }

        if (exit.isGuarded(mob)) {
            mob.out("The exit in direction " + exit.getId() + " is guarded.");
            return;
        }

        Room destination = exit.getDestinationRoom();

        if (destination == null) {
            mob.out("The exit in direction " + exit.getId() + " is not built yet!");
            return;
        }

        if (destination.hasFlag(RoomEnum.CLIMB) && !mob.isFlying()) {
            // check for climbing skill and boots
            Ability climbing = mob.getLearned().getAbility(CLIMBING);

            if (climbing == null || climbing.isNull()) {
                mob.out("You have no climbing ability to go that direction");
                return;
            }

            if (!mob.getEquipment().hasClimbingBoots()) {
                mob.out("You have no climbing boots to go that direction");
                return;
            }

            if (!climbing.isSuccessful(mob)) {
                mob.out("You fail to climb that exit");
                return;
            }


        }

        if (destination.hasFlag(RoomEnum.ADMIN) && !mob.isAdmin()) {
            mob.out("You must be admin to enter that location");
            return;
        }

        if (room.hasFlag(RoomEnum.AIR) && !mob.isFlying()) {
            mob.out("Down seems more likely");
            return;
        }

        if (destination.hasFlag(RoomEnum.AIR) && !mob.isFlying()) {
            mob.out("You must be flying to enter that location");
            return;
        }

        if (destination.hasFlag(RoomEnum.PRIVATE) && destination.getMobs().size() > 1) {
            mob.out("That destination is a private room which is already occupied.");
            return;
        }

        if (destination.hasFlag(RoomEnum.NARROW) && !destination.getMobs().isEmpty()) {
            mob.out("That destination is a tunnel room, which is already occupied.");
            return;
        }

        if (destination.hasFlag(RoomEnum.DEEP_WATER) && !mob.hasBoat()) {
            mob.out("You need a boat to cross that type of water");
            return;
        }

        int sectorMovement = destination.getSectorType().getMoveModify();
        String movement = "walk";

        Attribute moves = mob.getMv();

        if (mob.isRiding()) {
            moves = mob.getMount().getMv();
        }

        if (moves != null) {
            // walk fly swim teleport run sneak etc.....

            int movemod = mob.getBurden().getMovemod();

            if (room.hasFlag(RoomEnum.WATER) || exit.isSwim() && !mob.isFlying()) {
                if (!moves.deduct(10 * movemod)) {
                    mob.out("You can not swim you are out of movement and too tired!");
                    return;
                }
                movement = "swim";
            }
            if (mob.isRunning()) {
                if (!moves.deduct(10 * movemod)) {
                    mob.out("You can not run you are out of movement and too tired!");
                    return;
                }
                movement = "run";
            } else if (mob.isSneaking()) {
                if (!moves.deduct(4 * movemod)) {
                    mob.out("You can not sneak you are out of movement and too tired!");
                    return;
                }
                movement = "sneak";

            } else if (mob.isFlying()) {
                if (!moves.deduct(movemod)) {
                    mob.out("You can not fly you are out of movement and too tired!");
                    return;
                }
                movement = "fly";

            } else {
                if (!moves.deduct(sectorMovement * movemod)) {
                    mob.out("You can not walk you are out of movement and too tired!");
                    return;
                }
            }
        }

        // Clear any hiding flags when move to new location
        if (mob.isHidden()) {
            mob.setHidden(false);
        }

        // Might annoy players, but they can always load up on coffee to counter drunkenness.
        if (mob.isPlayer()) {
            Attribute drunk = mob.getPlayer().getData().getDrunkAttribute();
            if (drunk.getValue() > 100 && DiceRoll.ONE_D100.rollLessThanOrEqualTo(10)) {
                MoveManager.random(mob);
                mob.out("You are soo drunk you don't know if you walked the right way.");
                return;
            }
        }

        mob.out("You " + movement + " " + exit.getId());


        MoveManager.move(mob, room, destination, exit, movement);

        if (destination.hasFlag(RoomEnum.NARROW) && mob.isFlying()) {
            mob.out("You have entered a tunnel so stop flying for now.");
            mob.setState(STAND);
        }

        if (destination.hasFlag(RoomEnum.TRAP) && !mob.isAdmin()) {
            mob.getHp().setValue(-1);
            mob.out("You walk into a death trap and have been killed");
            DamageManager.checkForDefenderDeath(mob, mob);
        }

//        Track track = new Track();
//
//        if (mob.getHp().getPercentageLeft() < 10) {
//            track.setBlood(true);
//        }
//
//        track.setWho(mob.getName());
//        track.setDirection(exit.getId());
//
//        room.addTrack(track);

        // check room for followers and issue move command to any followers.
        for (Mob follower : room.getFollowers(mob)) {
            follower.out("You follow " + mob.getName() + " out of the room");
            CommandProvider.getCommand(EnterNoLook.class).execute(follower, exit.getId());
        }

        if (mob.isRiding()) {
            MoveManager.move(mob.getMount(), room, destination, exit, movement);
        }

        checkForVeryAggressiveMobs(mob, destination);

        checkForDiseases(mob, destination);

    }

    private void checkForDiseases(Mob mob, Room destination) {

        if (destination.getDiseases() == null) {
            return;
        }

        Iterator<Disease> diseaseIter = destination.getDiseases().iterator();
        while (diseaseIter.hasNext()) {
            Disease disease = diseaseIter.next();
            disease.tick();
            if (disease.isExpired()) {
                diseaseIter.remove();
            }
            Disease.infect(mob, disease);
        }


    }

    private void checkForVeryAggressiveMobs(Mob mob, Room destination) {
        // Check if any aggro or very aggressive mobs should attack you
        for (Mob mobInRoom : destination.getMobs()) {
            if (mobInRoom.hasMobEnum(MobEnum.AGGRO)) {
                LOGGER.debug("A very aggressive mob is here which launches into attack");
                Fight.startCombat(mobInRoom, mob);
            }
            if (mobInRoom.getFight().getLastMobAttackedMe() == mob) {
                LOGGER.debug("A mob remembers you from before and launches into attack");
                Fight.startCombat(mobInRoom, mob);
            }

        }
    }

}