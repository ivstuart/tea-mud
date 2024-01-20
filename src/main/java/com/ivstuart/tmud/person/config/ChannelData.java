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

package com.ivstuart.tmud.person.config;

import java.io.Serializable;
import java.util.Arrays;

/*
 * This class is simple a data class to store the configuration
 * for the players character
 */
public class ChannelData implements Serializable {

    public static final int AUCTION = 0;
    public static final int CLAN = 1;
    public static final int CHAT = 2;
    public static final int GROUP = 3;
    public static final int NEWBIE = 4;
    public static final int RAID = 5;
    /**
     *
     */
    private static final long serialVersionUID = -4084243242625557108L;
    private static final String[] FLAG_NAME = {"AUCTION", "CLAN", "CHAT",
            "GROUP", "NEWBIE", "RAID"};

    private static final String[] TRUE_DESCRIPTION = {
            "You will receive communication from auctions",
            "You will receive communication from your clan",
            "You will receive chat messages",
            "You will receive group tell messages",
            "You will receive chat from newbies",
            "You will receive get raid communication"};

    private static final String[] FALSE_DESCRIPTION = {
            "You will not receive communication from auctions",
            "You will not receive communication from your clan",
            "You will not receive chat messages",
            "You will not receive group tell messages",
            "You will not receive chat from newbies",
            "You will not receive get raid communication"};

    private final boolean[] mData = new boolean[6];

    public ChannelData() {
        Arrays.fill(mData, true);
    }

    public boolean is(int index) {
        return mData[index];
    }

    public void set(int index, boolean state) {
        mData[index] = state;
    }

    private void toggle(int index) {
        mData[index] = !mData[index];
    }

    public String toggle(String flagName) {
        for (int index = 0; index < mData.length; index++) {
            if (FLAG_NAME[index].equalsIgnoreCase(flagName)) {
                this.toggle(index);
                return toString(index);
            }
        }
        return "No such configuration option" + flagName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < mData.length; index++) {
            sb.append(String.format("[ %1$10s ] %2$s\n", FLAG_NAME[index],
                    this.toString(index)));
        }
        return sb.toString();
    }

    private String toString(int flag) {
        if (mData[flag]) {
            return TRUE_DESCRIPTION[flag];
        }
        return FALSE_DESCRIPTION[flag];
    }
}
