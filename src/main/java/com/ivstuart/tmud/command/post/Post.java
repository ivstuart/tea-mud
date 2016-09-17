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
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.post;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.utils.StringUtil;
import com.ivstuart.tmud.world.PostalSystem;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Post extends BaseCommand {

    /**
     *
     */
    public Post() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {

        Prop prop = mob.getRoom().getProps().get("post box");

        if (prop == null || !prop.getProperties().equals("postbox")) {
            mob.out("There is no post box here for posting or collection");
            return;
        }

        if (input.length() == 0) {
            collectPost(mob);
            return;
        }

        // TODO post note and or item to player
        String itemInput = StringUtil.getFirstWord(input);

        Item item = mob.getInventory().get(itemInput);

        if (item == null) {
            mob.out("There is no " + itemInput + " in your backpack");
            return;
        }

        String playerInput = StringUtil.getLastWord(input);

        if (!World.isPlayer(playerInput)) {
            mob.out("There is no one by that name known in the world");
            return;
        }

        int cost = 10 + item.getWeight() / 31;

        SomeMoney cash = mob.getInventory().getPurse().removeAndConvert(cost);
        if (cash == null) {
            mob.out("You do not have sufficient funds to post this item with costs " + cost + " copper to post");
            return;
        }
        mob.getInventory().setPurse(cash);

        mob.out("It costs you " + cost + " copper to post which leaves you with " + cash);

        mob.getInventory().remove(item);
        mob.out("You post a " + item.getBrief() + " to player " + playerInput);
        PostalSystem.post(item, playerInput);

    }

    private void collectPost(Mob mob) {

        Item item = PostalSystem.collect(mob.getPlayer().getName());

        if (item == null) {
            mob.out("You have no post waiting for you.");
            return;
        }

        mob.out("You collect a " + item.getBrief() + " from the post");
        mob.getInventory().add(item);


    }


}
