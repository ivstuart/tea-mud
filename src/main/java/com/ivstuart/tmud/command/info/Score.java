/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Info;
import com.ivstuart.tmud.constants.Profession;
import com.ivstuart.tmud.fighting.CombatCal;
import com.ivstuart.tmud.person.Guilds;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.PlayerData;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.Colour.*;
import static com.ivstuart.tmud.constants.ManaType.*;

/**
 * @author stuarti
 *         <p>
 *         <p>
 *         You have 0 copper banked, and carry 0 platinum, 0 gold and 0 copper.
 *         You carry 17 stones and 3 pebbles of weight. You are unburdened.
 */
public class Score extends BaseCommand {

    public static final int DAYS = 1000 * 60 * 60 * 24;
    public static final int HOURS = 1000 * 60 * 60;

    @Override
    public void execute(Mob mob, String input) {

        String mAtt = "" + CombatCal.getAttack(mob);
        String mDef = "" + CombatCal.getDefence(mob);

        String mBatt = "1";

        Player player = mob.getPlayer();

        PlayerData data = player.getData();

        mob.out(Info.BAR);
        mob.out("                      --- Pattern Character Sheet ---");
        mob.out(Info.BAR);
        mob.out("   You are " + mob.getId() + ", level " + data.getLevel()
                + " amongst your " + mob.getRace().getName() + " ancestors.\n");

        long playingTime = player.getData().getPlayingTime();

        int days = (int) playingTime / DAYS;
        int hours = (int) (playingTime - days * DAYS) / HOURS;

        mob.out("   You are " + player.getData().getAge() + " years of age ("
                + days + " Days, "
                + hours + " Hours) old. You are "
                + mob.getHeight() + "cm's tall.");
        mob.out("   You have gained " + player.getData().getTotalXp()
                + " experience points " + player.getData().getRemorts()
                + " remort.");
        mob.out("   You may learn " + data.getLearns()
                + " new skills or spells with your experience pool of "
                + data.getPracs() + "\n");

        mob.out(String
                .format("  %1$2s Health     : %2$6s    Damage Bonus : %3$6s    War Points  : %4$6s",
                        WHITE.toString(),
                        mob.getHp().getValue(),
                        player.getAPB(),
                        data.getWarpoints()));
        mob.out(String
                .format("  %1$2s Stamina    : %2$6s    Melee Attack : %3$6s    Kill Points : %4$6s",
                        WHITE.toString(),
                        mob.getMv().getValue(),
                        mAtt,
                        data.getKillpoints()));

        MobMana mana = mob.getMana();

        if (mana == null) {
            mob.out("   You have no magical power");

        } else {

            mob.out(String
                    .format("  %1$2s Mana       : %2$6s    Melee Defence: %3$6s    Ability Pts : %4$6s",
                            WHITE.toString(),
                            mana.getPrompt(),
                            mDef,
                            mob.getLearned().getAbilityPoints()));

            mob.out(String
                    .format("  %1$2s Fire  Save : %2$6s    Ranged Attack: %3$6s    Rating      : %4$6s",
                            RED.toString(),
                            mana.get(FIRE).getPrompt(),
                            mBatt,
                            Rating.getRating(mob)));
            mob.out(String
                    .format("  %1$2s Earth Save : %2$6s    Evasion      : %3$6s    Remorts     : %4$6s",
                            BROWN.toString(),
                            mana.get(EARTH).getPrompt(),
                            mob.getPlayer().getData().getEvasion(),
                            mob.getPlayer().getData().getRemorts()));
            mob.out(String
                    .format("  %1$2s Water Save : %2$6s    Stealth      : %3$6s    Level       : %4$6s",
                            BLUE.toString(),
                            mana.get(WATER).getPrompt(),
                            mob.getPlayer().getData().getStealth(),
                            data.getLevel()));
            mob.out(String
                    .format("  %1$2s Air   Save : %2$6s    Perception   : %3$6s    Deaths      : %4$6s",
                            YELLOW.toString(), mana.get(AIR).getPrompt(),
                            mob.getPlayer().getData().getPerception(),
                            mob.getPlayer().getData().getDeaths()));

        }

        mob.out("" + WHITE);

        mob.out(String
                .format("   You have %1$s banked, and carry %2$s",
                        mob.getPlayer().getBank().getPurseString(),
                        mob.getInventory().getPurseString()));
        mob.out(String
                .format("   You carry %1.4f kgs of weight. You are %2$10s",
                        mob.getWeightCarried() / 1000.0,
                        mob.getBurden().getDesc()));

        showGuilds(mob);

        showProfession(mob);

        mob.out("");

        mob.out(Info.BAR);

    }

    private void showProfession(Mob mob) {
        Profession profession = mob.getPlayer().getProfession();

        if (profession != null) {
            mob.out("   Your current profession is:"+profession.toString().toLowerCase());
        }
    }

    private void showGuilds(Mob mob) {
        Guilds guilds = mob.getPlayer().getGuilds();
        StringBuilder sb = new StringBuilder();

        if (guilds.isMages()) {
            sb.append("mages ");
        }
        if (guilds.isFighters()) {
            sb.append("fighters ");
        }
        if (guilds.isThieves()) {
            sb.append("thieves ");
        }
        if (guilds.isRangers()) {
            sb.append("rangers ");
        }
        if (guilds.isTinkers()) {
            sb.append("tinkers ");
        }
        if (guilds.isHealers()) {
            sb.append("healers ");
        }

        if (sb.length() >0) {
            mob.out("   Guilds: "+sb.toString());
        }

    }


}
