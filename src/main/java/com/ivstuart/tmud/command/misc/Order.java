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

package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;

public class Order extends BaseCommand {

    /**
     * Usage: order <character> <command> order followers <command>
     * <p>
     * Used for ordering pets and charmed people to do your bidding. You
     * can order everyone under your command with "order followers".
     * <p>
     * See admin "At" command and expand concept in this class.
     */
    @Override
    public void execute(Mob mob, String input) {

        // mob command args
        String[] args = input.split(" ", 3);

        if (args.length < 2) {
            mob.out("Usage: Order [mob] [command] [args...]");
            return;
        }

        Mob target = mob.getRoom().getMob(args[0]);

        if (target == null) {
            mob.out(input + " is not here to order");
            return;
        }

        if (target.getCharmed() != mob) {
            mob.out("You can not order that creature they are not charmed");
            return;
        }

        mob.out("You make mob " + target.getName() + " " + args[1] + " with " + args[2]);
        CommandProvider.getCommandByString(args[1]).execute(target, args[2]);


    }

}