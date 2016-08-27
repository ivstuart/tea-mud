/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SwapAlignment extends AdminCommand {

    /**
     * Instantely kill any mob
     */
    @Override
    public void execute(Mob mob, String input) {

        super.execute(mob, input);

        if (input.length() == 0) {
            mob.getPlayer().getData().getAlignment().invert();

            mob.out("You feel your alignment flip over");
            return;
        }

        Mob target = mob.getRoom().getMob(input);

        if (target == null) {
            mob.out(input + " is not here to smite!");
            return;
        }

        if (target.isPlayer()) {
            target.getPlayer().getData().getAlignment().invert();

            target.out("You feel your alignment flip over");
            return;
        }

        target.setAlignment(!target.isGood());

        mob.out("You feel " + mob.getName() + " alignment flip over");
    }

}
