/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.*;

import static com.ivstuart.tmud.constants.SkillNames.TRACKING;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Give extends BaseCommand {

	/**
	 * @param input
	 * @return
	 */
	private boolean checkCashGive(Mob mob, Mob target, String input) {
		SomeMoney sm = mob.getInventory().removeCoins(input);

		if (sm != null) {
			target.getInventory().add(sm);
			mob.out("You give " + sm+" to "+target.getName());
			return true;
		}

		return false;
	}

	@Override
	public void execute(Mob mob, String input) {

		String target = getLastWord(input);

		Mob targetMob = mob.getRoom().getMob(target);

		if (targetMob == null) {
			mob.out("There does not seem to be a "+target+" to give to here");
			return;
		}

		if (targetMob == mob) {
			mob.out("There is no point in giving it to yourself");
			return;
		}

		String itemString = input.split(" ")[0];

		if (checkCashGive(mob, targetMob, input)) {
			return;
		}

		Item item = mob.getInventory().get(itemString);

		if (item == null) {
			mob.out(input + " is not here to give!");
			return;
		}

		mob.getInventory().remove(item);
		mob.out("You give an " + item.getName());
		targetMob.getInventory().add(item);

		if(checkGiveTokenToGuildMaster(mob,targetMob,item)) {
			mob.out("Thank you for your token. Well come to the guild");
		}

		checkForContactDiseases(mob, targetMob);

	}

	private void checkForContactDiseases(Mob mob, Mob targetMob) {


		if (mob.getMobAffects().getDiseases() == null) {
			return;
		}

		for (Disease disease : mob.getMobAffects().getDiseases()) {
			if (disease.isDirectContact() || disease.isIndirectContact()) {
				Disease.infect(targetMob, disease);
			}
		}

	}

	private boolean checkGiveTokenToGuildMaster(Mob mob, Mob targetMob, Item item) {

		if (!item.getId().equals("guild-token-001")) {
			return false;
		}

		if (!(targetMob instanceof GuildMaster)) {
			return false;
		}

		GuildMaster guildMaster = (GuildMaster)targetMob;
		Player player = mob.getPlayer();

		if (player.getGuilds().getNumberOfGuilds() > 2) {
			mob.out("You may only join a maximum of 3 guilds choose wisely");
			return false;
		}

		if (guildMaster.isMages() && !player.getGuilds().isMages()) {
			player.getGuilds().setMages(true);
			player.getAttributes().getINT().increaseCurrentAndMaximum(1);
			return true;
		}
		if (guildMaster.isFighters() && !player.getGuilds().isFighters()) {
			player.getGuilds().setFighters(true);
			player.getAttributes().getSTR().increaseCurrentAndMaximum(1);
			return true;
		}
		if (guildMaster.isTinkers() && !player.getGuilds().isTinkers()) {
			player.getGuilds().setTinkers(true);
			player.getAttributes().getCON().increaseCurrentAndMaximum(1);
			return true;
		}
		if (guildMaster.isRangers() && !player.getGuilds().isRangers()) {
			player.getGuilds().setRangers(true);
			player.getAttributes().getDEX().increaseCurrentAndMaximum(1);
			BaseSkill baseSkill = World.getAbility(TRACKING);
			Ability ability = mob.getLearned().getAbility(TRACKING);
            if (ability.isNull()) {
                mob.getLearned().add(new Ability(baseSkill.getId()));
			}
			mob.getLearned().getAbility(TRACKING).setSkill(100);
			return true;
		}
		if (guildMaster.isHealers() && !player.getGuilds().isHealers()) {
			player.getGuilds().setHealers(true);
			player.getAttributes().getWIS().increaseCurrentAndMaximum(1);
			return true;
		}
		if (guildMaster.isThieves() && !player.getGuilds().isThieves()) {
			player.getGuilds().setThieves(true);
			player.getAttributes().getDEX().increaseCurrentAndMaximum(1);
			return true;
		}
		return false;


	}

	private String getLastWord(String input) {
		String words[] = input.split(" ");

		return words[words.length-1];
	}

}
