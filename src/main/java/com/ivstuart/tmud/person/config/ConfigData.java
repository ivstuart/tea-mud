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

import java.io.Serializable;
import java.util.EnumSet;

/*
 * This class is simple a data class to store the configuration
 * for the players character
 */
public class ConfigData implements Serializable {

    private static final long serialVersionUID = -4084243242625557108L;

    private final EnumSet<ConfigEnum> enumSet;

    public ConfigData() {
        enumSet = EnumSet.allOf(ConfigEnum.class);
    }

    public void flip(ConfigEnum configEnum) {
        if (enumSet.contains(configEnum)) {
            enumSet.remove(configEnum);
        } else {
            enumSet.add(configEnum);
        }
    }

    public boolean isFlagSet(ConfigEnum configEnum) {
        return enumSet.contains(configEnum);
    }

    public String getState(ConfigEnum configEnum) {
        if (isFlagSet(configEnum)) {
            return configEnum.getOn();
        } else {
            return configEnum.getOff();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ConfigEnum configEnum : ConfigEnum.values()) {
            sb.append(String.format("[ %1$10s ] %2$s\n", configEnum.name(), getState(configEnum)));
        }
        return sb.toString();
    }

    public void set(ConfigEnum configEnum) {
        enumSet.add(configEnum);
    }

    public void remove(ConfigEnum configEnum) {
        enumSet.remove(configEnum);
    }
}
