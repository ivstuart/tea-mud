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
 * Created on 24-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting.action;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttackFlurry extends BasicAttack {

    /**
     * Constructor
     *
     * @param me
     * @param target
     */
    public AttackFlurry(Mob me, Mob target) {
        super(me, target);

        out(new Msg(me, target,
                "<S-You ready your/NAME readies GEN-him>self to attack <T-you/NAME>."));

    }


    @Override
    public void happen() {

        if (!getSelf().getMv().deduct(25)) {
            out("You do not have enough movement available to flurry");
            this.finished();
        }

        getSelf().out("You open out with a flurry of attacks doubling your chances to hit");

        super.happen();
        super.happen();

    }


}
