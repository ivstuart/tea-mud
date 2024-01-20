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

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 15/08/2016.
 */
public class BaseBehaviour implements Tickable {

    protected Mob mob;
    protected int parameter;
    protected int parameter2;
    protected String parameter3;

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
    }

    public int getParameter2() {
        return parameter2;
    }

    public void setParameter2(int parameter2) {
        this.parameter2 = parameter2;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean tick() {
        return false;
    }

    public void setMob(Mob mob) {
        this.mob = mob;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
