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

public class FightConstants {

    public static final String[] miss = {
            "<S-You/NAME> attack and miss wide of a <T-NAME>",
            "<S-Your/NAME> attack misses wide of the <T-NAME>",
            "<S-You/NAME> fail<S-/s> to find an opening in the <T-NAME>'s defences",
            "<S-Your/NAME's> series of attacks fail to connect with a <T-NAME>",
            "<S-You/NAME> find no opportunity to strike at a <T-NAME>",
            "<S-You/NAME> just miss a <T-NAME> by the smallest margin.",
            "<S-You/NAME> completely muss a <T-NAME>",
            "<S-You/NAME> barely miss a <T-NAME>",
            "<S-You/NAME> badly miss a <T-NAME> leaving yourself open to attack."};

    public static final String[] healthStatus = {"$GDying$J", // Red
            "$CAwful$J", // Orange
            "$FBad$J", // Grey?
            "$NHurt$J", // Purple
            "$IPain$J", // Blue
            "$DGood$J", // Green
            "$LFine$J"}; // Light Green

    public static final String[] healthLook = {
            "<T-NAME> is hovering on deaths door!",
            "<T-NAME> is covered in blood.",
            "<T-NAME> is bleeding badly from lots of wounds.",
            "<T-NAME> has numerous bloody wounds and gashes.",
            "<T-NAME> has some bloody wounds and gashes.",
            "<T-NAME> has a few bloody wounds.",
            "<T-NAME> is cut and bruised.",
            "<T-NAME> has some minor cuts and bruises.",
            "<T-NAME> has a few bruises and scratches.",
            "<T-NAME> has a few small bruises.",
            "<T-NAME> is in perfect health."};

    public static final String[] consider = {
            "You carefully go over the battle in your mind...",
            "You will have fun rearranging a wretched soul's body parts!",
            "Why would you bore yourself killing a distant traveler...",
            "This should be fairly easy...",
            "You truly are a masochist! There is NO WAY!"};
}
