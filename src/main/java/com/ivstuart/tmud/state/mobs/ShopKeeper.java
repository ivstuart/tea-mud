/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.state.mobs;

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
