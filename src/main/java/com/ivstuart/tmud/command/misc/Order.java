/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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