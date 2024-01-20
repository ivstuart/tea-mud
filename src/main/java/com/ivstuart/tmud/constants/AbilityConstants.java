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

package com.ivstuart.tmud.constants;

public class AbilityConstants {

    // **** [You have become better at <SKILL>!] ****

    public static final String SHIELD_BLOCK = "shield block";
    public static String[] abilityString = {"untrained", "unskilled",
            "pathetic", "bad", "poor", "mediocre", "some skill", "fine",
            "competent", "proficient", "capable", "good", "skilled",
            "talented", "masterful", "gifted", "expert", "excellent",
            "amazing", "godly"};

    public static String getSkillString(int skill_) {
        int index = (skill_ * abilityString.length) / 100;

        if (index >= abilityString.length) {
            index = abilityString.length - 1;
        }

        return abilityString[index];
    }
}
