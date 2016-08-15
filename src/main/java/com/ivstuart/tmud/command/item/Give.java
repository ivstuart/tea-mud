/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.*;

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
		// TODO Auto-generated method stub
		int type = -1;
		if (input.indexOf("copper") > 0) {
			type = Money.COPPER;
		}
		if (input.indexOf("silver") > 0) {
			type = Money.SILVER;
		}
		if (input.indexOf("gold") > 0) {
			type = Money.GOLD;
		}
		if (type > -1) {
			String inputSplit[] = input.split(" ");
			if (inputSplit == null) {
				return false;
			}
			int coins = 0;
			try {
				coins = Integer.parseInt(inputSplit[0]);
			} catch (NumberFormatException e) {
				return false;
			}

			Money cash = new Money(type, coins);

			if (mob.getInventory().getPurse().remove(cash)) {
				mob.out("You give "+cash+" to "+target.getName());
				target.getInventory().add(cash);
				return true;
			}

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

		if (itemString.equalsIgnoreCase("all")) {
			giveAllCoins(mob,targetMob);
			return;
		}

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
	}

	private String getLastWord(String input) {
		String words[] = input.split(" ");

		return words[words.length-1];
	}

	private void giveAllCoins(Mob mob,Mob target) {

		SomeMoney money = mob.getInventory().getPurse();

		if (money != null) {
			mob.out("No money to give");
			return;
		}

		target.getInventory().add(money);

		money.clear();
	}

}
