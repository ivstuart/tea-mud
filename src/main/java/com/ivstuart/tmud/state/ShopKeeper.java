package com.ivstuart.tmud.state;

import com.ivstuart.tmud.constants.Profession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 16/08/2016.
 */
public class ShopKeeper extends Mob {

    private boolean noGood;
    private boolean noEvil;
    private boolean noNeutral;
    private List<Profession> noProfession;

    public ShopKeeper(ShopKeeper baseMob) {
        super(baseMob);
        this.noGood = baseMob.noGood;
        this.noEvil = baseMob.noEvil;
        this.noNeutral = baseMob.noNeutral;
        this.noProfession = baseMob.noProfession;
    }

    public ShopKeeper() {
        super();
    }

    public boolean isNoGood() {
        return noGood;
    }

    public void setNoGood(boolean noGood) {
        this.noGood = noGood;
    }

    public boolean isNoEvil() {
        return noEvil;
    }

    public void setNoEvil(boolean noEvil) {
        this.noEvil = noEvil;
    }

    public boolean isNoNeutral() {
        return noNeutral;
    }

    public void setNoNeutral(boolean noNeutral) {
        this.noNeutral = noNeutral;
    }

    public boolean isNoProfession(Profession profession) {
        if (noProfession == null) {
            return false;
        }
        return noProfession.contains(profession);
    }

    public void setNoProfession(String noProfession) {
        if (this.noProfession == null) {
            this.noProfession = new ArrayList<>(4);
        }
        this.noProfession.add(Profession.valueOf(noProfession));
    }
}
