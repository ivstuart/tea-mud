package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.admin.Battleground;
import com.ivstuart.tmud.command.item.Get;
import com.ivstuart.tmud.command.item.Sacrifice;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.constants.DamageConstants;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.config.ConfigData;
import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.ivstuart.tmud.constants.SpellNames.*;

public class DamageManager {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void deal(Mob attacker, Mob defender, int damage) {

        if (defender.isMemory()) {
            defender.getFight().setLastMobAttackedMe(attacker);
        }

        if (!checkInSameRoom(attacker, defender)) {
            if (attacker != null) {
                attacker.getFight().stopFighting();
                attacker.out("Your target is in another room!");
            }
            if (defender != null) {
                defender.getFight().stopFighting();
            }
            return;
        }

        // Give creatures that are spell casted at a chance to fight back.
        if (!defender.getFight().isFighting() && damage > 0) {
            if (attacker != defender) {
                defender.getFight().getMelee().setTarget(attacker);
                WorldTime.addFighting(defender);
            }
        }

        // Skip saves for ground fighting
        if (defender.getMobStatus().isGroundFighting()) {
            defender.getHp().decrease(damage);
            checkForDefenderDeath(attacker, defender);
            return;
        }

        if (checkIfTargetSleeping(defender)) {
            damage *= 2;
            defender.setState(MobState.WAKE);
        } else {


            // Check saves first
            damage = checkForDodge(defender, damage);

            damage = checkForParry(defender, damage);

            // 10% save chance
            damage = checkForBlur(attacker, defender, damage);
        }

        // Half damage
        damage = checkForSanctury(attacker, defender, damage, SANCTURY);

        // Stoneskin does not stack with spell stoneskin
        if (defender.getRace().isStoneskin()) {
            damage /= 2;
        } else {
            damage = checkForSanctury(attacker, defender, damage, STONE_SKIN);
        }

        damage = checkForSanctury(attacker, defender, damage, BARRIER);

        damage = checkForShieldBlocking(defender, damage);

        // Combat sense
        damage = checkForCombatSense(attacker, damage);

        // Take off armour at hit location
        //   if melee the armor at location
        //      apply any armor peneration skills or specials to counter armour saves
        //   if spell damage the average armour minus any special elemental saves

        int armour = checkArmourAtHitLocation(defender);

        if (armour > 0) {
            // TODO no arm pen for spells
            armour = checkForArmourPenetration(attacker, armour);
        }

        damage = damage - armour;

        if (damage < 1) {
            attacker.out("Your feeble blow is deflected by their armour");
            return;
        }

        checkMagicalDamageSaves();

        Msg msg = new Msg(attacker, defender, DamageConstants.toString(damage));

        attacker.getRoom().out(msg);

        // TODO apply xp for combat damage and count this (clear counter on kill) after reporting xp from fighting.
        defender.getHp().decrease(damage);

        checkForDefenderDeath(attacker, defender);

    }

    private static boolean checkIfTargetSleeping(Mob defender) {
        return defender.getState().isSleeping();
    }

    // 20% more damage with combat sense.
    private static int checkForCombatSense(Mob attacker, int damage) {

        Affect buff = attacker.getMobAffects().getAffect(COMBAT_SENSE);

        if (buff != null) {
            damage *= 1.2;
        }

        return damage;
    }

    private static int checkForSanctury(Mob attacker, Mob defender, int damage, String affect) {
        Affect sanc = defender.getMobAffects().getAffect(affect);

        if (sanc == null) {
            return damage;
        }

        return sanc.onHit(attacker, defender, damage);
    }


    private static int checkForBlur(Mob attacker, Mob defender, int damage) {
        Affect immortalBlur = defender.getMobAffects().getAffect(IMP_BLUR);

        if (immortalBlur != null) {
            LOGGER.debug("Cheating with improved blur");
            return 0;
        }

        Affect blur = defender.getMobAffects().getAffect(BLUR);

        if (blur == null) {
            return damage;
        }

        return blur.onHit(attacker, defender, damage);

    }

    private static boolean checkInSameRoom(Mob attacker, Mob defender) {
        if (attacker == null || defender == null) {
            return false;
        }
        return attacker.getRoom() == defender.getRoom();
    }

    private static void checkMagicalDamageSaves() {
        // TODO Fire Water Earth Air and Magic saves to check
        // acts rather like armour

    }

    private static int checkArmourAtHitLocation(Mob defender) {

        if (!defender.isPlayer()) {
            return defender.getArmour();
        }

        Armour armour = defender.getEquipment().getTotalArmour();
        try {
            return armour.getRandomSlot();
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    private static int checkForArmourPenetration(Mob attacker, int armour) {
        if (armour < 1) {
            return armour;
        }

        Ability penetration = attacker.getLearned().getAbility(SkillNames.ARMOUR_PENETRATION);

        if (penetration != null && penetration.isSuccessful()) {
            attacker.out(new Msg(attacker, "<S-You/NAME> skillfully hit between your opponents armour"));

            armour /= DiceRoll.ONE_D_SIX.roll();

            if (penetration.isImproved()) {
                attacker.out("[[[[ Your ability to " + attacker.getId()
                        + " has improved ]]]]");
                penetration.improve();
            }
        }
        return Math.max(armour, 0);
    }

    public static void checkForDefenderDeath(Mob attacker, Mob defender) {
        // check for defender death!
        synchronized (defender) {
            if (defender.getHp().getValue() <= 0) {
                defender.out("You have been killed!\n\n");

                attacker.getFight().clear();
                defender.getFight().clear();

                attacker.getMobStatus().clear();
                defender.getMobStatus().clear();

                if (defender.isPlayer()) {
                    defender.getPlayer().getData().incrementDeaths();
                }

                createCorpse(attacker, defender);

                if (defender.isPlayer()) {
                    Room portal = World.getPortal(defender);
                    portal.add(defender);
                    defender.setRoom(portal);
                    defender.getHp().setValue(1);

                    // Allocate WP's for opposite alignment kill
                    allocateWarpoints(attacker, defender);

                    // Update counters to battle ground if one is running
                    Battleground.deathOf(defender);

                } else {
                    // Add mob to list of the dead ready for repopulation after a
                    // timer.
                    WorldTime.scheduleMobForRepopulation(defender);

                    // Award xp to killing mob.
                    int xp = defender.getXp();

                    if (attacker.isPlayer()) {
                        attacker.getPlayer().getData().addKill(defender);

                        if (null != attacker.getPlayer().getGroup()) {
                            int totalLevel = 0;
                            for (Mob aMob : attacker.getPlayer().getGroup()) {
                                if (aMob.getRoom() != defender.getRoom()) {
                                    continue;
                                }
                                totalLevel += aMob.getPlayer().getData().getLevel();
                            }

                            // Have to be in the same room to gain the xp from the group kill
                            for (Mob aMob : attacker.getPlayer().getGroup()) {
                                if (aMob.getRoom() != defender.getRoom()) {
                                    continue;
                                }
                                int level = aMob.getPlayer().getData().getLevel();

                                int xpSplit = (level * xp) / totalLevel;

                                aMob.out("You gained [" + xpSplit + "] total experience for the kill by being in a group.");

                                aMob.getPlayer().getData().addXp(xpSplit);

                                aMob.getPlayer().checkIfLeveled();

                            }


                        } else {
                            attacker.out("You gained [" + xp
                                    + "] total experience for the kill.");

                            attacker.getPlayer().getData().addXp(xp);
                        }

                        attacker.getPlayer().checkIfLeveled();

                        attacker.out("You hear a " + defender.getName() + "'s death cry.");
                    }

                }

                // clear any affects
                attacker.getFight().stopFighting();
                defender.getFight().stopFighting();
                //
            }
        }
    }

    private static void allocateWarpoints(Mob attacker, Mob defender) {
        int level = 0;
        if (attacker.isGood() != defender.isGood()) {
            level = defender.getPlayer().getData().getLevel();

            if (null != attacker.getPlayer().getGroup()) {
                int numberInGroup = 0;
                for (Mob aMob : attacker.getPlayer().getGroup()) {
                    if (aMob.getRoom() != defender.getRoom()) {
                        continue;
                    }
                    numberInGroup++;
                }
                level /= numberInGroup;

                for (Mob aMob : attacker.getPlayer().getGroup()) {
                    aMob.out("You gain " + level + " warpoints.");
                    aMob.getPlayer().getData().incrementWarpoints(level);
                }


            } else {
                attacker.out("You gain " + level + " warpoints.");
                attacker.getPlayer().getData().incrementWarpoints(level);
            }
        }

    }

    private static void createCorpse(Mob attacker, Mob defender) {
        // create corpse and move equipment and inventory to corpse
        Corpse corpse = new Corpse();
        corpse.setId("corpse");
        corpse.setAlias("corpse");

        if (defender.getRoom().isNoDrop() == false) {
            inveToCorpse(defender, corpse);
        } else {
            LOGGER.debug("No drop has been set for this room so eq will not be dropped to corpse");
        }

        // corpse.setShort("Corpse of "+defender.getName());
        corpse.setBrief("The corpse of a filthy " + defender.getName()
                + " is lying here.");

        defender.getRoom().remove(defender);
        defender.getRoom().add((Prop) corpse);

        // AUTO LOOT
        if (attacker.getPlayer() != null) {
            if (attacker.getPlayer().getConfig().getConfigData().is(ConfigData.AUTOLOOT)) {
                // get all from corpse.
                CommandProvider.getCommand(Get.class).execute(attacker, "all from corpse");
            }

            // AUTO SAC
            if (attacker.getPlayer().getConfig().getConfigData().is(ConfigData.AUTOSAC)) {
                // get all from corpse.
                CommandProvider.getCommand(Sacrifice.class).execute(attacker, "corpse");
            }
        }

    }

    private static void inveToCorpse(Mob defender, Corpse corpse) {

        LOGGER.debug("Moving inventory to corpse");

        // Items
        corpse.getInventory().addAll(defender.getInventory().getItems());
        defender.getInventory().clear();

        // Coins
        corpse.getInventory().add(defender.getInventory().getPurse());
        defender.getInventory().getPurse().clear();

        // Equip
        List<Equipable> equipables = defender.getEquipment().removeAll();

        for (Equipable eq : equipables) {
            Item item = (Item) eq;
            if (item.isNoDrop()) {
                // defender.getInventory().add(item);
                defender.getEquipment().add(item);
            } else {
                corpse.getInventory().add(item);
            }
        }

        // Mob copper
        if (!defender.isPlayer()) {
            SomeMoney money = new Money(Money.COPPER, defender.getCopper());
            corpse.getInventory().getPurse().add(money);
        }

    }

    private static int checkForDodge(Mob defender, int damage) {
        Ability dodge = defender.getLearned().getAbility("dodge");

        // Reduce dodging to 30% of the time.
        if (dodge != null && dodge.isSuccessful() && DiceRoll.ONE_D_SIX.rollMoreThan(4)) {
            defender.out(new Msg(defender, "<S-You/NAME> successfully dodge missing most of the attack."));

            damage = (int) damage / 10;

            if (dodge.isImproved()) {
                defender.out("[[[[ Your ability to " + dodge.getId()
                        + " has improved ]]]]");
                dodge.improve();
            }
        }

        return damage;

    }

    private static int checkForParry(Mob defender, int damage) {
        Ability parry = defender.getLearned().getAbility("parry");

        // Reduce parry to 50% of the time.
        if (parry != null && parry.isSuccessful() && DiceRoll.ONE_D_SIX.rollMoreThan(2)) {
            defender.out(new Msg(defender, "<S-You/NAME> successfully parry missing most of the attack."));

            damage = (int) damage / 5;

            if (parry.isImproved()) {
                defender.out("[[[[ Your ability to " + parry.getId()
                        + " has improved ]]]]");
                parry.improve();
            }
        }

        return damage;

    }

    public static int checkForShieldBlocking(Mob defender, int damage) {
        Ability shieldBlock = defender.getLearned().getAbility(SkillNames.SHIELD_BLOCK);

        if (shieldBlock != null) {
            if (defender.getEquipment().hasShieldEquiped()) {
                if (shieldBlock.isSuccessful()) {
                    defender.out(new Msg(defender, "<S-You/NAME> successfully shield block <T-you/NAME>."));

                    damage -= 5;

                    if (shieldBlock.isImproved()) {
                        defender.out("[[[[ Your ability to "
                                + shieldBlock.getId() + " has improved ]]]]");
                        shieldBlock.improve();
                    }
                }
            }
        }
        return damage;
    }
}
