/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.fighting.action.BasicSpell;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.ivstuart.tmud.common.MobState.FLYING;
import static com.ivstuart.tmud.common.MobState.STAND;
import static com.ivstuart.tmud.utils.StringUtil.getFirstFewWords;
import static com.ivstuart.tmud.utils.StringUtil.getLastWord;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Cast extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

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

        if (mob_.getRoom().isNoMagic()) {
            mob_.out("You can not use magic in this room");
            return;
        }
        // cast magic missile at target
        // cast smite <defaults to current target>
        // cast acid rain <defaults to enemies??>
        // cast fireball me/self/all/evil/good/mob

        String concatWords = getFirstFewWords(input_);


        Ability spellAbility = mob_.getLearned().getAbility(concatWords);

        if (spellAbility == null || spellAbility.isNull()) {
            spellAbility = mob_.getLearned().getAbility(input_);
            concatWords = input_;
        }

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

        execute(mob_, spell, spellAbility, input_, true);

    }

    public void execute(Mob mob_, Spell spell, Ability spellAbility, String input_, boolean castingCost) {
        if (mob_.getRoom().isPeaceful() && !spell.getSpellEffect().isPositiveEffect()) {
            mob_.out("You can not use offensive magic in this room");
            return;
        }

        if (mob_.getFight().isGroundFighting()) {
            mob_.out("You can not cast spells while ground fighting");
            return;
        }

        // String target = getLastWord(input_, concatWords.length(), null);

        String target = getLastWord(input_);

        // check you have mana and a casting level which is required.
        LOGGER.debug("Spell mana type [" + spell.getManaType() + "]");

        MobMana mana = null;
        if (castingCost) {
            mana = mob_.getMana();

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
        }

        // Alias is used to support target value of
        /* me, self, all, good, evil, etc.. */

        Mob targetMob = null;

        if (target != null) {
            if (checkTargetManySpell(mob_, target, spellAbility, spell, mana, castingCost)) return;

            if (checkTargetItemSpell(mob_, target, spellAbility, spell, mana, castingCost)) return;

            targetMob = mob_.getRoom().getMob(target);

        }

        // When fighting can target attacker with damage spell with no target set
        if (!spell.getSpellEffect().isPositiveEffect() && mob_.getFight().isFighting()) {
            if (targetMob == null) {
                LOGGER.debug("Damage spell so targeting current melee target");
                targetMob = mob_.getFight().getTarget();
            }
        }

        if (targetMob == null) {
            if (targettingSelf(target)) {
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

        if (castingCost) {
            mana.cast(spell);
        }

        mob_.out("You begin to chant and make unusual gestures");

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

    // check equip
    // check inve
    // check room props
    // check room items
    // check world
    private boolean checkTargetItemSpell(Mob mob_, String target, Ability spellAbility, Spell spell, MobMana mana, boolean castingCost) {
        if (spell.getTarget() != null && spell.getTarget().indexOf("ITEM") > -1) {
            Item item = mob_.getRoom().getInventory().get(target);

            if (item == null) {
                Prop prop = mob_.getRoom().getProps().get(target);

                if (prop instanceof Item) {
                    item = (Item) prop;
                    if (item == null) {
                        // Local object spell
                        item = World.getItem(target);
                        if (item == null) {
                            mob_.out("The item " + target + " is not here to target");
                            return true;
                        }
                    }
                }
            }

            if (item.isInvisible() && !mob_.hasDetectInvisible()) {
                mob_.out("The item " + target + " is not seen here to target");
                return true;
            }

            if (castingCost) {
                mana.cast(spell);
            }

            mob_.out("You begin to chant and make very complex gestures");

            mob_.out("You start casting " + spell.getId() + " at " + item.getName());

            FightAction spellFightAction = new BasicSpell(spellAbility, spell,
                    mob_, item);

            mob_.getFight().add(spellFightAction);

            return true;
        }
        return false;
    }

    private boolean checkTargetManySpell(Mob mob_, String target, Ability spellAbility, Spell spell, MobMana mana, boolean castingCost) {
        if (spell.getTarget() != null && spell.getTarget().indexOf("MANY") > -1) {
            List<Mob> targets = mob_.getRoom().getMobs(target);

            if (targets.isEmpty()) {

                mob_.out(target + " is not here to target!");
                return true;
            }

            if (castingCost) {
                mana.cast(spell);
            }

            mob_.out("You begin to chant and make unusually complex gestures");

            mob_.out("You start casting area effect " + spell.getId());

            FightAction spellFightAction = new BasicSpell(spellAbility, spell,
                    mob_, targets.get(0), targets);

            mob_.getFight().add(spellFightAction);
            return true;
        }
        return false;
    }

    private boolean targettingSelf(String target) {
        return target == null || target.length() == 0 || target.equalsIgnoreCase("me") || target.equalsIgnoreCase("self");
    }

}
