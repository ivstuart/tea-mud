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

/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.person;

import com.ivstuart.tmud.person.config.ChannelData;
import com.ivstuart.tmud.person.config.ConfigData;
import com.ivstuart.tmud.person.config.FightData;

import java.io.Serializable;

/**
 * @author stuarti
 */
public class Config implements Serializable {

    private static final long serialVersionUID = -2704055301344799543L;

    private final ConfigData configData;

    private final ChannelData channelData;

    private final FightData fightData;

    /**
     *
     */
    public Config() {
        super();
        configData = new ConfigData();
        channelData = new ChannelData();
        fightData = new FightData();
    }

    @Override
    public String toString() {
        return "Config{" +
                "configData=" + configData +
                ", channelData=" + channelData +
                ", fightData=" + fightData +
                '}';
    }

    public ChannelData getChannelData() {
        return channelData;
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public FightData getFightData() {
        return fightData;
    }

}
