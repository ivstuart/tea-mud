package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.state.Mob;

public class SavesInfo extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        for (DamageType damageType : DamageType.values()) {
            mob.out(damageType + " " + mob.getSave(damageType));
        }
    }
}