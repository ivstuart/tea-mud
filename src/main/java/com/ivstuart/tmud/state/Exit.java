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

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.ExitEnum;
import com.ivstuart.tmud.world.World;

public class Exit extends BasicThing {

    private static final long serialVersionUID = 520626372370843140L;
    private String destinationRoomId;
    private transient Room destinationRoom; // optimization
    private Door door;
    private boolean isSpecial;

    public Exit() {
    }

    public Exit(String id, String destination_) {
        this.setId(id);
        isSpecial = isSpecial(id);
        destinationRoomId = destination_;
    }

    @Override
    public String toString() {
        return "Exit{" +
                "destinationRoomId='" + destinationRoomId + '\'' +
                // ", destinationRoom=" + destinationRoom +
                ", door=" + door +
                ", isSpecial=" + isSpecial +
                '}';
    }

    public Room getDestinationRoom() {
        if (destinationRoom == null) {
            destinationRoom = World.getRoom(destinationRoomId);
        }
        return destinationRoom;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door_) {
        door = door_;
    }

    public boolean isGuarded(Mob mob_) {

        if (getDestinationRoom() == null) {
            return false;
        }
        // Destination room is wrong need to be in same room as guards minimum
        for (Mob mob : mob_.getRoom().getMobs()) {
            if (mob.isGuarding(mob_, this.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isScanable() {
        if (door == null) {
            return true;
        }
        return door.getState().isScanable();
    }

    public String look() {

        String exitDescription;

        if (door == null) {
            exitDescription = this.getId();
        } else {
            exitDescription = door.state.getBegin() + this.getId()
                    + door.state.getEnd();
        }

        if (this.isHidden()) {
            exitDescription += "(hidden)";
        }

        if (this.isInvisible()) {
            exitDescription += "(invisible)";
        }

        getDestinationRoom();

        if (destinationRoom != null) {
            if (destinationRoom.hasFlag(RoomEnum.WATER)) {
                exitDescription = "<" + exitDescription + ">";
            }

            if (destinationRoom.hasFlag(RoomEnum.CLIMB)) {
                exitDescription = "/" + exitDescription + "/";
            }

            if (destinationRoom.hasFlag(RoomEnum.AIR)) {
                exitDescription = "{" + exitDescription + "}";
            }
        }

        return exitDescription;

    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public boolean isSpecial(String name) {
        for (ExitEnum exit : ExitEnum.values()) {
            if (exit.getDesc().equals(name)) {
                return false;
            }

        }
        return true;
    }

    public boolean isSwim() {
        return getDestinationRoom().hasFlag(RoomEnum.WATER);
    }
}
