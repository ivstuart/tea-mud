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

package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.fighting.action.BasicAttack;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ivan Stuart
 */
public class Fight {

    private static final int MAX_SIZE = 10;

    private static final Logger LOGGER = LogManager.getLogger();
    private LinkedList<FightAction> fightActions;
    private FightAction melee;
    private Mob lastMobAttackedMe;
    private List<Mob> targettedBy;

    /**
     *
     */
    public Fight(FightAction melee) {
        this.melee = melee;
        fightActions = new LinkedList<>();
        targettedBy = new LinkedList<>();
    }

    public Fight(Mob mob) {
        this(new BasicAttack(mob, null));
    }

    public static void startCombat(Mob mob, Mob target) {
        startCombat(mob, target, false);
    }

    public static void startCombat(Mob mob, Mob target, boolean retarget) {

        mob.getFight().getMelee().setTarget(target);

        if (retarget || target.getFight().getMelee().getTarget() == null) {
            target.getFight().getMelee().setTarget(mob);
            target.getFight().changeTarget(mob);
        }

        WorldTime.addFighting(mob);

        WorldTime.addFighting(target);
    }

    /**
     * Used only for speeding up test cases.
     *
     * @return
     */
    public LinkedList<FightAction> getFightActions() {
        return fightActions;
    }

    public Mob getLastMobAttackedMe() {
        return lastMobAttackedMe;
    }

    public void setLastMobAttackedMe(Mob lastMobAttackedMe) {
        this.lastMobAttackedMe = lastMobAttackedMe;
    }

    public void add(FightAction action) {

        if (fightActions.size() > MAX_SIZE) {
            out("Ignoring command as too many have been queued.");
            return;
        }

        // i.e. for bash want to start the combat. Cast blur we do not want to start combat against self.
        // same goes for healing spells they should not start melee combat.
        if (fightActions.isEmpty()) {

            if (melee.getTarget() == null) {
                // Check not targeting self
                if (melee.getSelf() == action.getTarget()) {
                    LOGGER.debug("Not setting target target is self");
                } else if (!action.isMeleeEnabled()) {
                    LOGGER.debug("Not setting target not a melee enabled action");
                } else {
                    melee.setTarget(action.getTarget());
                }
            }
        }

        fightActions.add(action);

        WorldTime.addFighting(action.getSelf());
    }

    private boolean addTargettedBy(Mob mob) {
        return targettedBy.add(mob);
    }

    public void changeTarget(Mob newTargetMob) {

        if (melee == null) {
            LOGGER.debug("Can not change targets to " + newTargetMob + " because melee is null");
            return;
        }

        //LOGGER.debug(melee.getSelf().getName() + " changeTarget "+ newTargetMob.getName());

        Mob ownTarget = melee.getTarget();

        if (ownTarget != null) {
            ownTarget.getFight().removeTargettedBy(melee.getSelf());

            LOGGER.debug(melee.getSelf().getName() + " removed from targeted by for " + ownTarget.getName());
        }

        melee.setTarget(newTargetMob);

        if (newTargetMob != null) {

            newTargetMob.getFight().addTargettedBy(melee.getSelf());
            LOGGER.debug(melee.getSelf().getName() + " added to targeted by for " + newTargetMob.getName());

        }

    }

    /**
     *
     */
    public void clear() {
        targettedBy.clear();
        fightActions.clear();

    }

    public FightAction getMelee() {
        return melee;
    }

    public void setMelee(FightAction melee) {
        this.melee = melee;
    }

    public Mob getSelf() {
        if (melee == null) {
            return null;
        }
        return melee.getSelf();
    }

    public Mob getTarget() {
        if (melee == null) {
            return null;
        }
        return melee.getTarget();
    }

    public List<Mob> getTargettedBy() {
        return targettedBy;
    }

    /**
     * @return
     */
    public boolean isEngaged() {
        return !targettedBy.isEmpty();
    }

    public boolean isEngaged(Mob combatant) {
        return targettedBy.contains(combatant);
    }

    public boolean isFighting() {
        if (melee == null) {
            return false;
        }
        return melee.getTarget() != null;
    }

    public boolean hasFightActions() {
        return !fightActions.isEmpty();
    }

    public boolean isGroundFighting() {
        if (melee == null) {
            return false;
        }
        return melee.isGroundFighting();
    }

    private void out(String message) {
        if (melee == null) {
            return;
        }
        melee.getSelf().out(message);
    }

    public FightAction remove() {
        return fightActions.remove();
    }

    private boolean removeTargettedBy(Mob mob) {
        return targettedBy.remove(mob);
    }

    public void resolveCombat() {

        if (fightActions.isEmpty()) {

            //LOGGER.debug("Fight action is empty foe "+this.getMelee().getSelf().getName()+" target is "+this.getTarget().getName());

            resolveMelee();

        } else {

            FightAction fightAction = fightActions.getFirst();

            if (fightAction.isFinished()) {
                //LOGGER.debug("fightAction.isFinished() hence removing action");
                fightActions.remove();
            }

            if (fightAction.isMeleeEnabled()) {
                //LOGGER.debug("Melee enabled so one round of melee fighting");

                resolveMelee();

            }

            fightAction.next();

        }

    }

    private void resolveMelee() {

        if (melee == null) {
            return;
        }

        if (melee.getSelf() == melee.getTarget()) {
            this.stopFighting();
            return;
        }

        if (melee.getTarget() != null) {
            melee.next();
            if (melee.isFinished()) {
                melee.restart();

            }
        }
    }

    public void setMeleeToBasicAttack() {
        this.melee = new BasicAttack(melee.getSelf(), melee.getTarget());
    }

    public boolean stopFighting() {

        clear();

        if (melee == null) {
            return false;
        }

        melee.getSelf().setFight(null);

        if (melee.getTarget() == null) {
            melee = null;
            return false;
        }

        changeTarget(null);
        melee = null;
        return true;
    }

    /**
     * Stack overflow if include targettedBy
     *
     * @return
     */
    @Override
    public String toString() {
        return "Fight{" +
                "fightActions=" + fightActions +
                ", melee=" + melee +
                //", targettedBy=" + targettedBy.size() +
                '}';
    }

    public boolean isBeingCircled(Mob self) {
        for (Mob mob : targettedBy) {
            if (mob == self) {
                continue;
            }
            if (mob.getMobStatus().isCircling()) {
                return true;
            }
        }
        return false;
    }


}