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

public class Facing {


    public static int reverse(int facing) {

        if (facing == 4) {
            return 5;
        }
        if (facing == 5) {
            return 4;
        }

        return (facing + 2) % 4;
    }

    public static String getDirection(int facing) {
        switch (facing) {
            case 0:
                return "west";
            case 1:
                return "north";
            case 2:
                return "east";
            case 3:
                return "south";
            case 4:
                return "up";
            case 5:
                return "down";
        }
        return null;
    }

    public static String getOpposite(String direction) {
        switch (direction) {
            case "east":
                return "west";
            case "south":
                return "north";
            case "west":
                return "east";
            case "north":
                return "south";
            case "up":
                return "down";
            case "down":
                return "up";
        }
        return direction;
    }

    public static boolean isCustom(String direction) {
        String opposite = getOpposite(direction);

        return opposite.equals(direction);
    }
}
