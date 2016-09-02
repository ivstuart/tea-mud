/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.utils.StringUtil;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Bid extends BaseCommand {

    /**
     *
     */
    @Override
    public void execute(Mob mob, String input) {

        String sellerName = StringUtil.getFirstWord(input);

        String coins = input.substring(1 + sellerName.length());

        SomeMoney money = mob.getInventory().removeCoins(coins);

        if (money == null) {
            mob.out("No bid with " + coins + " command format, bid [name] [amount]");
            return;
        }
        AuctionItem ai = World.getAuction(sellerName);

        if (ai == null) {
            mob.out("That person " + sellerName + " has no auctions running");
        }

        if (ai.bid(mob, money)) {
            mob.out("You have the highest bid and are winning the auction");
            return;
        } else {
            mob.out("You are currently being outbid");
        }

    }

}
