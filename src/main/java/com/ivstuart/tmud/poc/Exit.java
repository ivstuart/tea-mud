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

package com.ivstuart.tmud.poc;

public class Exit {

    private final String name;

    private final GridLocation destination;

    private boolean door = false;

    public Exit(String name, GridLocation destination) {
        this.name = name;
        this.destination = destination;
    }

    public Exit(int facing, GridLocation destination) {
        this.name = Facing.getDirection(facing);
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public GridLocation getDestination() {
        return destination;
    }

    public boolean isDoor() {
        return door;
    }


    @Override
    public String toString() {
        return "Exit{" +
                "name='" + name + '\'' +
                ", destination=" + destination +
                ", door=" + door +
                '}';
    }

    public void toggleDoor() {
        door = !door;
    }
}
