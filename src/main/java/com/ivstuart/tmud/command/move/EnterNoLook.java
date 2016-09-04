package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            mob.out("You can not move while hidding!");
            return;
        }

        if (mob.getMv() != null && mob.getMv().getValue() < 0) {
            mob.out("You can not move you are out of movement and too tired!");
            return;
        }

        if (!mob.getState().canMove()) {
            mob.out("You can not move you are " + mob.getState().getDesc());
            return;
        }


        Exit exit = room.getExit(input);

        if (exit == null) {
            mob.out("No visible exit in direction " + input + ".");
            return;
        }

        Door door = exit.getDoor();

        if (door != null && door.getState() != DoorState.OPEN) {
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

        if (destination.isClimb() && !mob.isFlying()) {
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

            if (!DiceRoll.ONE_D100.rollLessThanOrEqualTo(climbing.getSkill())) {
                mob.out("You fail to climb that exit");
                return;
            }

            if (climbing.isImproved()) {
                mob.out(">>>>> [You have become better at " + climbing.getId() + "!] <<<<<");
                climbing.improve();
            }

        }

        if (destination.isAdminRoom() && !mob.isAdmin()) {
            mob.out("You must be admin to enter that location");
            return;
        }

        if (destination.isFlying() && !mob.isFlying()) {
            mob.out("You must be flying to enter that location");
            return;
        }

        if (destination.isPrivate() && destination.getMobs().size() > 1) {
            mob.out("That destination is a private room which is already occupied.");
            return;
        }

        if (destination.isTunnel() && destination.getMobs().size() != 0) {
            mob.out("That destination is a tunnel room, which is already occupied.");
            return;
        }

        if (destination.isDeepWater() && !mob.hasBoat()) {
            mob.out("You need a boat to cross that type of water");
            return;
        }

        int sectorMovement = destination.getSectorType().getMoveModify();
        String movement = "walk";

        if (mob.getMv() != null) {
            // walk fly swim teleport run sneak etc.....

            int movemod = mob.getBurden().getMovemod();

            if (room.isWater() || exit.isSwim() && !mob.isFlying()) {
                if (!mob.getMv().deduct(10 * movemod)) {
                    mob.out("You can not swim you are out of movement and too tired!");
                    return;
                }
                movement = "swim";
            }
            if (mob.isRunning()) {
                if (!mob.getMv().deduct(10 * movemod)) {
                    mob.out("You can not run you are out of movement and too tired!");
                    return;
                }
                movement = "run";
            } else if (mob.isSneaking()) {
                if (!mob.getMv().deduct(4 * movemod)) {
                    mob.out("You can not sneak you are out of movement and too tired!");
                    return;
                }
                movement = "sneak";

            } else if (mob.isFlying()) {
                if (!mob.getMv().deduct(1 * movemod)) {
                    mob.out("You can not fly you are out of movement and too tired!");
                    return;
                }
                movement = "fly";

            } else {
                if (!mob.getMv().deduct(sectorMovement * movemod)) {
                    mob.out("You can not walk you are out of movement and too tired!");
                    return;
                }
            }
        }

        // Clear any hidding flags when move to new location
        if (mob.isHidden()) {
            mob.setHidden(false);
        }

        mob.out("You " + movement + " " + exit.getId());


        MoveManager.move(mob, room, destination, exit, movement);

        if (destination.isTunnel() && mob.isFlying()) {
            mob.out("You have entered a tunnel so stop flying for now.");
            mob.setState(STAND);
        }

        if (destination.isDeath() && !mob.isAdmin()) {
            mob.getHp().setValue(-1);
            mob.out("You walk into a death trap and have been killed");
            DamageManager.checkForDefenderDeath(mob, mob);
        }

        Track track = new Track();

        if (mob.getHp().getPercentageLeft() < 10) {
            track.setBlood(true);
        }

        track.setWho(mob.getName());
        track.setDirection(exit.getId());

        room.addTrack(track);

        // check room for followers and issue move command to any followers.
        for (Mob follower : room.getFollowers(mob)) {
            follower.out("You follow " + mob.getName() + " out of the room");
            CommandProvider.getCommand(EnterNoLook.class).execute(follower, exit.getId());
        }

        // Check if any aggro or very aggressive mobs should attack you
        for (Mob mobInRoom : destination.getMobs()) {
            if (mobInRoom.isVeryAggressive()) {
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