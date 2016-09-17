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

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.person.statistics.diseases.DiseaseFactory;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.StringUtil;

/**
 * Created by Ivan on 05/09/2016.
 */
public class GiveDisease extends AdminCommand {

    @Override
    public void execute(Mob mob, String input) {

        String target = StringUtil.getLastWord(input);
        String disease = StringUtil.getFirstFewWords(input);

        Mob targetMob = mob.getRoom().getMob(target);

        if (target.equals("me")) {
            targetMob = mob;
        }

        if (targetMob == null) {
            mob.out("You see no " + target + " here to target");
            return;
        }

        Disease infection = DiseaseFactory.createClass(disease);
        infection.setMob(targetMob);
        infection.setDecription(disease);
        infection.setDuration(infection.getInitialDuration());
        targetMob.getMobAffects().add(infection.getId(), infection);

        mob.out("You give " + infection.getDesc() + " to mob " + targetMob.getName());

    }
}
