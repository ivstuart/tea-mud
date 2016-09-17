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

package com.ivstuart.tmud.common;

/**
 * Created by Ivan on 20/08/2016.
 */
public enum ExitEnum {

    n("north",0,1,0),
    s("south",0,-1,0),
    e("east",1,0,0),
    w("west",-1,0,0),
    u("up",0,0,1),
    d("down",0,0,-1);

    private final String desc;
    private final int dx;
    private final int dy;
    private final int dz;

    ExitEnum(String s, int dx, int dy, int dz) {
        this.desc = s;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public String getDesc() {
        return desc;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getDz() {
        return dz;
    }
}
