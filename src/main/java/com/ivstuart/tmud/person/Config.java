/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
 * 
 */
public class Config implements Serializable {

	private static final long serialVersionUID = -2704055301344799543L;

	private ConfigData configData;

	private ChannelData channelData;

	private FightData fightData;

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
