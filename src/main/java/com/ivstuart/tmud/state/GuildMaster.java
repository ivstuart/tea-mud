package com.ivstuart.tmud.state;

/**
 * Created by Ivan on 16/08/2016.
 */
public class GuildMaster extends Mob {

    private boolean fighters;
    private boolean tinkers;
    private boolean rangers;
    private boolean healers;
    private boolean mages;
    private boolean thieves;

    public GuildMaster(Mob baseMob) {
        super(baseMob);
        GuildMaster gm = (GuildMaster)baseMob;
        this.fighters = gm.fighters;
        this.tinkers = gm.tinkers;
        this.rangers = gm.rangers;
        this.healers = gm.healers;
        this.mages = gm.mages;
        this.thieves = gm.thieves;
    }

    public GuildMaster() {
        super();
    }

    public boolean isFighters() {
        return fighters;
    }

    public void setFighters(boolean fighters) {
        this.fighters = fighters;
    }

    public boolean isTinkers() {
        return tinkers;
    }

    public void setTinkers(boolean tinkers) {
        this.tinkers = tinkers;
    }

    public boolean isRangers() {
        return rangers;
    }

    public void setRangers(boolean rangers) {
        this.rangers = rangers;
    }

    public boolean isHealers() {
        return healers;
    }

    public void setHealers(boolean healers) {
        this.healers = healers;
    }

    public boolean isMages() {
        return mages;
    }

    public void setMages(boolean mages) {
        this.mages = mages;
    }

    public boolean isThieves() {
        return thieves;
    }

    public void setThieves(boolean thieves) {
        this.thieves = thieves;
    }
}
