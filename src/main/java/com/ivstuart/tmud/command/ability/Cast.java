/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.person.Player;
import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.fighting.action.BasicSpell;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.World;

import java.util.List;

import static com.ivstuart.tmud.common.MobState.STAND;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Cast implements Command {

    private static final Logger LOGGER = Logger.getLogger(Cast.class);

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob_, String input_) {

        // Check if can cast ie if standing.
        if (mob_.getState() != STAND) {
            mob_.out("You must be standing to cast spells");
            return;
        }

        // cast magic missile at target
        // cast smite <defaults to current target>
        // cast acid rain <defaults to enemies??>
        // cast fireball me/self/all/evil/good/mobs

		/* Ok so parse input_ to get number of words */
        String words[] = input_.split(" ");

        String target = "me";

        Ability spellAbility = null;

        String concatWords = "";
        for (String word : words) {
            concatWords += word;
            spellAbility = mob_.getLearned().getAbility(concatWords);
            if (spellAbility != null && !spellAbility.isNull()) {
                break;
            }
            concatWords += " ";
        }

        if (spellAbility == null) {
            mob_.out("You have no knowledge of spell " + input_);
            return;
        }

        Spell spell = World.getSpell(spellAbility.getId());

        if (spell == null) {
            LOGGER.warn("Spell ability:" + spellAbility.getId());
            mob_.out("You know " + input_ + " which is not castable according to world list");
            return;
        }

        if (concatWords.length() < input_.length()) {
            target = words[words.length - 1];
        }

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

        if (spell.getTarget() != null && spell.getTarget().indexOf("MANY") > -1) {
            List<Mob> targets = mob_.getRoom().getMobs(target);

            if (targets.isEmpty()) {

                mob_.out(target + " is not here to target!");
                return;
            }
            mana.cast(spell);

            mob_.out("You begin to chant and make unusally complex gestures");

            mob_.out("You start casting area effect " + spell.getId());

            FightAction spellFightAction = new BasicSpell(spellAbility, spell,
                    mob_, targets.get(0), targets);

            mob_.getFight().add(spellFightAction);
            return;
        }

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

                }
                else {
                    mob_.out(target + " is not here to target!");
                    return;
                }
            }
        }

        if (spell.getTarget() != null && spell.getTarget().indexOf("SELF") > -1) {
            if (targetMob != mob_) {
                mob_.out("You can only target self with this spell");
                return;
            }
        }

        mana.cast(spell);

        mob_.out("You begin to chant and make unusally gestures");

        mob_.out("You start casting " + spell.getId());

        FightAction spellFightAction = new BasicSpell(spellAbility, spell,
                mob_, targetMob);

        mob_.getFight().add(spellFightAction);

    }

    private boolean targettingSelf(String target) {
        return target.length() == 0 || target.equalsIgnoreCase("me") || target.equalsIgnoreCase("self");
    }

}
