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
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Affects extends BaseCommand {

    /*
     * (non-Javadoc)
     *
     * @see command.Command#execute(java.lang.String)
     */
    @Override
    public void execute(Mob mob, String input) {


        // get any mob affects
        mob.out(mob.showMobAffects());

        mob.out(getHunger(mob));
        mob.out(getThirst(mob));

    }

    private String getHunger(Mob self) {
        Attribute att = self.getPlayer().getData().getHunger();
        if (att.getValue() > 400) {
            return "You are fully fed.";
        }
        if (att.getValue() > 150) {
            return "You could eat if forced.";
        }
        if (att.getValue() > 100) {
            return "You are slightly hungry.";
        }
        if (att.getValue() > 50) {
            return "You are hungry.";
        }
        if (att.getValue() > 0) {
            return "You are very hungry.";
        }
        if (att.getValue() > -50) {
            return "You are suffering with starvation!";
        }
        return "You are starving to death!";
    }

    private String getThirst(Mob self) {
        Attribute att = self.getPlayer().getData().getThirst();
        if (att.getValue() > 80) {
            return "You are fully refreshed.";
        }
        if (att.getValue() > 30) {
            return "You could drink if forced to.";
        }
        if (att.getValue() > 20) {
            return "You are slightly thirsty.";
        }
        if (att.getValue() > 10) {
            return "You are thirsty.";
        }
        if (att.getValue() > 0) {
            return "You are very thirsty.";
        }
        if (att.getValue() > -10) {
            return "You are suffering with dehydration!";
        }
        return "Dehydration is killing you!";
    }

}
