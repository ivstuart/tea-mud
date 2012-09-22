/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.person;

import java.io.*;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ivstuart.tmud.person.config.*;

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

	public ChannelData getChannelData() {
		return channelData;
	}

	public ConfigData getConfigData() {
		return configData;
	}

	public FightData getFightData() {
		return fightData;
	}

	@Override
	public final String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
