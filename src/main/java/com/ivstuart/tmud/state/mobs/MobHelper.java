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

public class MobHelper {

    /**
     * @return age string
     */
    public static String getAge(Mob mob) {
        if (mob.isPlayer()) {
            int remorts = mob.getPlayer().getData().getRemorts();
            switch (remorts) {
                case 0:
                    return "young";
                case 1:
                    return "youthful";
                case 2:
                    return "middle aged";
                case 3:
                    return "mature";
                case 4:
                    return "old";
                case 5:
                    return "very old";
                case 6:
                    return "ancient";
                default:
                    return "unknown";
            }
        }
        return "";
    }


    /**
     * Based on level
     *
     * @return size of mob
     */
    public static String getSize(int level) {
        if (level < 10) {
            return "small";
        } else if (level < 20) {
            return "medium";
        } else if (level < 40) {
            return "big";
        } else if (level < 60) {
            return "large";
        } else if (level < 80) {
            return "huge";
        } else {
            return "massive";
        }
    }
}
