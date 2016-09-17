/*
 *  Copyright 2016. Ivan Stuart
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
import com.ivstuart.tmud.utils.StringUtil;
import com.ivstuart.tmud.world.World;

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
