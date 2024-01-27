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
import java.util.EnumSet;

/*
 * This class is simple a data class to store the configuration
 * for the players character
 */
public class ChannelData implements Serializable {

    private static final long serialVersionUID = -4084243242625557108L;

    private final EnumSet<ChannelEnum> enumSet;

    public ChannelData() {
        enumSet = EnumSet.allOf(ChannelEnum.class);
    }

    public void flip(ChannelEnum channelEnum) {
        if (enumSet.contains(channelEnum)) {
            enumSet.remove(channelEnum);
        }
        else {
            enumSet.add(channelEnum);
        }
    }

    public boolean isFlagSet(ChannelEnum channelEnum) {
        return enumSet.contains(channelEnum);
    }

    public String getState(ChannelEnum channelEnum) {
        if (isFlagSet(channelEnum)) {
            return channelEnum.getOn();
        }
        else {
            return channelEnum.getOff();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ChannelEnum channelEnum : ChannelEnum.values()) {
            sb.append(String.format("[ %1$10s ] %2$s\n",channelEnum.name(), getState(channelEnum)));
        }
        return sb.toString();
    }
}
