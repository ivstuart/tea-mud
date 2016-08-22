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
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.utils.*;
import static com.ivstuart.tmud.utils.StringUtil.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Get extends BaseCommand {

	/**
	 * @param input
	 * @return
	 */
	private boolean checkCashGet(Mob mob, String input) {
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

			if (mob.getRoom().remove(cash)) {
				mob.getInventory().add(cash);
			}
		}

		return false;
	}

	/**
	 * > get sword corpse
	 * > get all corpse
	 * > get all from corpse
	 * > get all all.bag
	 * > get all.bread
	 * all.bag
	 */
	@Override
	public void execute(Mob mob, String input) {

		// TODO Auto-generated method stub

		if (input.equalsIgnoreCase("all")) {
			getAllCoins(mob);
			return;
		}

		if (checkCashGet(mob, input)) {
			return;
		}

		String lastWord = StringUtil.getLastWord(input);
		Prop prop = mob.getRoom().getProps().get(lastWord);

		if (prop != null) {
			if (prop instanceof Corpse) {
				Corpse corpse = (Corpse) prop;
				String target = StringUtil.getFirstFewWords(input);
				SomeMoney sm = corpse.getInventory().removeCoins(target);

				if( sm != null) {
					mob.getInventory().add(sm);
					mob.out("You get "+sm+" from "+corpse.getName());
					return;
				}

				Item item = corpse.getInventory().get(target);

				if (item != null) {
					corpse.getInventory().remove(item);
					mob.getInventory().add(item);
					mob.out("You get "+item+" from "+corpse.getName());
				}

			}
		}

		MudArrayList<Item> items = mob.getRoom().getItems();

		if (items == null) {
			mob.out(input + " is not here to get!");
			return;
		}

		// TODO this is broken
		if (input.equalsIgnoreCase("all")) {
			Iterator<Item> itemIter = items.iterator();
			for (Item item = itemIter.next();itemIter.hasNext();item = itemIter.next()) {
				mob.getInventory().add(item);

				mob.out("You get an " + item.getName());
				itemIter.remove();
			}
			return;
		}

		Item anItem = items.remove(input);

		// Check if getting an item out from a bag
		if (anItem == null) {

			String target = getLastWord(input);
			anItem = mob.getInventory().get(target);

			if (anItem != null && anItem.isContainer()) {
				Bag bag = (Bag)anItem;
				String itemString = getFirstWord(input);
				Item bagItem = bag.getInventory().get(itemString);

				if (bagItem != null) {
					mob.getInventory().add(bagItem);
					bag.getInventory().remove(bagItem);
					mob.out("You get a "+bagItem.getName()+" from "+bag.getName());
					return;
				}

			}

		}

		if (anItem == null) {
			mob.out("Can not get " + input + " it is not here!");
			return;
		}

		mob.getInventory().add(anItem);

		mob.out("You get an " + anItem.getName());
	}

	// TODO threadsafety
	private void getAllCoins(Mob mob) {

		SomeMoney money = mob.getRoom().getMoney();

		if (money != null) {
			mob.getInventory().add(money);
			money.clear();
		}
	}

}
