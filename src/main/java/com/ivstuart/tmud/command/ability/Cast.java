/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.*;
import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.fighting.action.BasicSpell;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.person.statistics.MobMana;

import java.util.List;

import static com.ivstuart.tmud.common.MobState.FLYING;
import static com.ivstuart.tmud.common.MobState.STAND;
import static com.ivstuart.tmud.utils.StringUtil.*;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Cast extends BaseCommand {

    private static final Logger LOGGER = Logger.getLogger(Cast.class);

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob_, String input_) {

        // Check if can cast ie if standing.
        if (mob_.getState() != STAND && mob_.getState() != FLYING) {
            mob_.out("You must be standing or flying to cast spells");
            return;
        }

        // cast magic missile at target
        // cast smite <defaults to current target>
        // cast acid rain <defaults to enemies??>
        // cast fireball me/self/all/evil/good/mob

        String concatWords = getFirstFewWords(input_);

        Ability spellAbility = mob_.getLearned().getAbility(concatWords);

        if (spellAbility == null || spellAbility.isNull()) {
            mob_.out("You have no knowledge of spell " + input_);
            return;
        }

        Spell spell = World.getSpell(spellAbility.getId());

        if (spell == null) {
            LOGGER.warn("Spell ability:" + spellAbility.getId());
            mob_.out("You know " + input_ + " which is not castable according to world list");
            return;
        }

        String target = getLastWord(input_,concatWords.length(),"me");

        // check you have mana and a casting level which is required.
        LOGGER.debug("Spell mana type [" + spell.getManaType() + "]");

        MobMana mana = mob_.getMana();

        if (mana == null) {
            mob_.out("You have no magical power");
            return;
        }

        if (!mana.hasLevelToCast(spell)) {
            mob_.out("You do not have sufficient ability to cast "
                    + spell.getId());
            return;
        }

        if (!mana.hasEnoughManaToCast(spell)) {
            mob_.out("You do not have sufficient mana to cast " + spell.getId());
            return;
        }


        // TODO work out a way to support target value of
        /* me, self, all, good, evil, etc.. */

        if (checkTargetManySpell(mob_, target, spellAbility, spell, mana)) return;

        if (checkTargetItemSpell(mob_, target, spellAbility, spell, mana)) return;

        Mob targetMob = mob_.getRoom().getMob(target);

        if (targetMob == null) {
            // TODO spells need to default targets depending on if they do damage or not.
            Mob fightTarget = mob_.getFight().getTarget();
            if (fightTarget != null) {
                targetMob = fightTarget;
            } else if (targettingSelf(target)) {
                targetMob = mob_;
            } else {
                if (spell.isAnyTarget()) {
                    LOGGER.debug("Getting mob from the world");

                    Player player = World.getPlayer(target);

                    if (player != null) {
                        targetMob = player.getMob();
                    }

                    if (targetMob == null) {
                        mob_.out(target + " is not in world to target!");
                    }

                } else {
                    mob_.out(target + " is not here to target!");
                    return;
                }
            }
        }

        if (checkTargetSelf(mob_, spell, targetMob)) return;

        mana.cast(spell);

        mob_.out("You begin to chant and make unusally gestures");

        mob_.out("You start casting " + spell.getId());

        FightAction spellFightAction = new BasicSpell(spellAbility, spell,
                mob_, targetMob);

        mob_.getFight().add(spellFightAction);

    }



    private boolean checkTargetSelf(Mob mob_, Spell spell, Mob targetMob) {
        if (spell.getTarget() != null && spell.getTarget().indexOf("SELF") > -1) {
            if (targetMob != mob_) {
                mob_.out("You can only target self with this spell");
                return true;
            }
        }
        return false;
    }

    // TODO
    // check equip
    // check inve
    // check room props
    // check room items
    // check world
    private boolean checkTargetItemSpell(Mob mob_, String target, Ability spellAbility, Spell spell, MobMana mana) {
        if (spell.getTarget() != null && spell.getTarget().indexOf("ITEM") > -1) {
            Item item = mob_.getRoom().getItems().get(target);

            if (item == null) {
                // TODO decide if props still makes sense to have - danagerous cast.
                item = (Item)mob_.getRoom().getProps().get(target);
                if (item == null) {
                    // TODO get Item from the World for locate object spell
                    // World.getItem(target);
                    mob_.out("The item " + target + " is not here to target");
                    return true;
                }
            }

            // TODO check is item visible to player
            mana.cast(spell);

            mob_.out("You begin to chant and make very complex gestures");

            mob_.out("You start casting " + spell.getId() + " at " + item.getName());

            FightAction spellFightAction = new BasicSpell(spellAbility, spell,
                    mob_, item);

            mob_.getFight().add(spellFightAction);

            return true;
        }
        return false;
    }

    private boolean checkTargetManySpell(Mob mob_, String target, Ability spellAbility, Spell spell, MobMana mana) {
        if (spell.getTarget() != null && spell.getTarget().indexOf("MANY") > -1) {
            List<Mob> targets = mob_.getRoom().getMobs(target);

            if (targets.isEmpty()) {

                mob_.out(target + " is not here to target!");
                return true;
            }
            mana.cast(spell);

            mob_.out("You begin to chant and make unusally complex gestures");

            mob_.out("You start casting area effect " + spell.getId());

            FightAction spellFightAction = new BasicSpell(spellAbility, spell,
                    mob_, targets.get(0), targets);

            mob_.getFight().add(spellFightAction);
            return true;
        }
        return false;
    }

    private boolean targettingSelf(String target) {
        return target.length() == 0 || target.equalsIgnoreCase("me") || target.equalsIgnoreCase("self");
    }

}
