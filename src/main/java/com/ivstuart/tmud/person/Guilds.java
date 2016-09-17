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

package com.ivstuart.tmud.person;

import java.io.Serializable;

/**
 * Created by Ivan on 02/09/2016.
 */
public class Guilds implements Serializable {

    private boolean fighters;
    private boolean tinkers;
    private boolean rangers;
    private boolean healers;
    private boolean mages;
    private boolean thieves;

    @Override
    public String toString() {
        return "Guilds{" +
                "fighters=" + fighters +
                ", tinkers=" + tinkers +
                ", rangers=" + rangers +
                ", healers=" + healers +
                ", mages=" + mages +
                ", thieves=" + thieves +
                '}';
    }

    public int getNumberOfGuilds() {
        int counter = 0;
        if (isMages()) {
            counter++;
        }
        if (isFighters()) {
            counter++;
        }
        if (isThieves()) {
            counter++;
        }
        if (isRangers()) {
            counter++;
        }
        if (isTinkers()) {
            counter++;
        }
        if (isHealers()) {
            counter++;
        }
        return counter;
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
