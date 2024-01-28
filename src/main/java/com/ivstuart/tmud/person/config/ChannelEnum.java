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

package com.ivstuart.tmud.person.config;

public enum ChannelEnum {

    AUCTION("You will receive communication from auctions"),
    CLAN("You will receive communication from your clan"),
    CHAT("You will receive chat messages"),
    GROUP("You will receive group tell messages"),
    NEWBIE("You will receive chat from newbies"),
    RAID("You will receive get raid communication");


    private final String on;
    private final String off;

    ChannelEnum(String on) {
        this.on = on;
        this.off = on.replaceFirst("receive", "NOT receive");
    }

    public String getOn() {
        return on;
    }

    public String getOff() {
        return off;
    }
}
