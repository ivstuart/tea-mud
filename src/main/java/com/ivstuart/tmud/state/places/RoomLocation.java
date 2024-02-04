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

package com.ivstuart.tmud.state.places;

import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable class
 */
public class RoomLocation implements Serializable {


    private static final long serialVersionUID = 1L;

    public static final RoomLocation PORTAL_GOOD = new RoomLocation(0,0,0);
    public static final RoomLocation PORTAL_EVIL = new RoomLocation(1,1,0);

    public static final RoomLocation DONATE_GOOD = PORTAL_GOOD;

    public static final RoomLocation DONATE_EVIL = PORTAL_EVIL;

    private final int x;
    private final int y;
    private final int z;

    public RoomLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomLocation that = (RoomLocation) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "RoomLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Deprecated
    public RoomIdentifier getRoomIdentifer() {
        return new RoomIdentifier(x,y);
    }
}
