/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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

        Mob targetMob = mob.getRoom().getMob(target.toLowerCase());

        Disease infection = DiseaseFactory.createClass(disease);
        infection.setMob(targetMob);
        infection.setDecription(disease);
        infection.setDuration(infection.getInitialDuration());
        targetMob.getMobAffects().add(infection.getId(), infection);

        mob.out("You give " + infection.getDesc() + " to mob " + targetMob.getName());

    }
}
