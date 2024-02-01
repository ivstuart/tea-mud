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

import com.ivstuart.tmud.common.ExitEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan on 20/08/2016.
 */
@Deprecated
public class RoomIdentifier {

    private static final Logger LOGGER = LogManager.getLogger();
    private int x, y, z = 0;
    private String roomPrefix;

    public RoomIdentifier() {
    }

    public RoomIdentifier(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static RoomIdentifier getRoomId(Room room) {
        String[] tokens = room.getId().split(":");

        if (tokens.length != 4) {
            return new RoomIdentifier();
        }

        RoomIdentifier newRoomId = new RoomIdentifier();
        newRoomId.setRoomPrefix(tokens[0]);
        newRoomId.x = Integer.parseInt(tokens[1]);
        newRoomId.y = Integer.parseInt(tokens[2]);
        newRoomId.z = Integer.parseInt(tokens[3]);
        return newRoomId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getRoomPrefix() {
        return roomPrefix;
    }

    public void setRoomPrefix(String roomPrefix) {
        this.roomPrefix = roomPrefix;
    }

    @Override
    public String toString() {
        return roomPrefix + ":" + x + ":" + y + ":" + z;
    }

    public RoomIdentifier createNewRoomId(ExitEnum exit) {

        RoomIdentifier newRoomId = new RoomIdentifier();
        newRoomId.setRoomPrefix(roomPrefix);
        newRoomId.x = this.x;
        newRoomId.y = this.y;
        newRoomId.z = this.z;
        newRoomId.x += exit.getDx();
        newRoomId.y += exit.getDy();
        newRoomId.z += exit.getDz();
        return newRoomId;
    }
}
