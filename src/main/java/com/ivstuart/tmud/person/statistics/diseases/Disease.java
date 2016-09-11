/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person.statistics.diseases;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan on 06/09/2016.
 */
public class Disease extends Affect implements Tickable, Cloneable {

    private static final Logger LOGGER = LogManager.getLogger();
    private boolean directContact; // Give item
    private boolean airbourne; // Flags the room for sometime
    private boolean indirectContact; // Drop item flag on item
    private boolean droplets; // Same room
    private boolean oral; // Eatting food
    private boolean vector; // Fly biting - prop based requires insects.
    private int infectionRate;
    private int cureRate;
    private int initialDuration;

    public Disease() {
        super();
    }

    public Disease(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
    }

    public static void infect(Mob mob, Disease disease) {
        if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(disease.getInfectionRate())) {
            Disease infection = (Disease) disease.clone();
            infection.setMob(mob);
            infection.setDuration(disease.getInitialDuration());
            mob.getMobAffects().add(disease.getId(), infection);
            LOGGER.debug(mob.getName() + " infected with " + disease.getDesc());
        }
    }

    @Override
    public String toString() {
        return "Disease{" +
                "directContact=" + directContact +
                ", airbourne=" + airbourne +
                ", indirectContact=" + indirectContact +
                ", droplets=" + droplets +
                ", oral=" + oral +
                ", vector=" + vector +
                ", infectionRate=" + infectionRate +
                ", cureRate=" + cureRate +
                ", initialDuration=" + initialDuration +
                '}';
    }

    public int getInitialDuration() {
        return initialDuration;
    }

    public void setInitialDuration(int initialDuration) {
        this.initialDuration = initialDuration;
    }

    public boolean isDirectContact() {
        return directContact;
    }

    public void setDirectContact(boolean directContact) {
        this.directContact = directContact;
    }

    public boolean isAirbourne() {
        return airbourne;
    }

    public void setAirbourne(boolean airbourne) {
        this.airbourne = airbourne;
    }

    public boolean isIndirectContact() {
        return indirectContact;
    }

    public void setIndirectContact(boolean indirectContact) {
        this.indirectContact = indirectContact;
    }

    public boolean isDroplets() {
        return droplets;
    }

    public void setDroplets(boolean droplets) {
        this.droplets = droplets;
    }

    public boolean isOral() {
        return oral;
    }

    public void setOral(boolean oral) {
        this.oral = oral;
    }

    public boolean isVector() {
        return vector;
    }

    public void setVector(boolean vector) {
        this.vector = vector;
    }

    public int getInfectionRate() {
        return infectionRate;
    }

    public void setInfectionRate(int infectionRate) {
        this.infectionRate = infectionRate;
    }

    public int getCureRate() {
        return cureRate;
    }

    public void setCureRate(int cureRate) {
        this.cureRate = cureRate;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Problem cloning object", e);
        }
        return null;
    }

    @Override
    public String getId() {
        return this.getDesc();
    }

    public boolean tick() {
        if (_mob != null && _mob.isPlayer()) {
            _duration--;
        }
        return false;
        // poison would -1 to health or more...
    }
}
