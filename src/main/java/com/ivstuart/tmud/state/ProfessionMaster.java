package com.ivstuart.tmud.state;

import com.ivstuart.tmud.constants.Profession;

/**
 * Created by Ivan on 16/08/2016.
 */
public class ProfessionMaster extends Mob {

    private Profession prof;

    public ProfessionMaster() {
        super();
    }

    public ProfessionMaster(ProfessionMaster existingMob) {
        super(existingMob);
        prof = existingMob.prof;
    }

    public Profession getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = Profession.valueOf(prof);
    }
}
