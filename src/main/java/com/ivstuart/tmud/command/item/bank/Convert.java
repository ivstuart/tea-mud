/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.item.bank;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.MoneyBag;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Convert extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        Mob banker = mob.getRoom().getBanker();

        if (banker == null) {
            mob.out("There is no bank here to convert currency");
            return;
        }

        if (checkCashDeposit(mob, banker, input)) {
            return;
        }


    }

    private boolean checkCashDeposit(Mob mob, Mob banker, String input) {

        SomeMoney cash = mob.getInventory().removeCoins(input);

        if (cash == null) {
            return false;
        }

        int type = Money.getType(input);

        MoneyBag moneyBag = new MoneyBag(cash.getValue(),type,true);

        mob.getInventory().add(moneyBag);

        mob.out("You convert your " + cash + " with the bank into " + moneyBag);

        return true;
    }

}
