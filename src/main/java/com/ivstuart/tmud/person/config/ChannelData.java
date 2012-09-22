package com.ivstuart.tmud.person.config;

import java.io.*;

/*
 * This class is simple a data class to store the configuration 
 * for the players character
 */
public class ChannelData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4084243242625557108L;

	// TODO - rather old school - an enum would be better
	public static final int AUCTION = 0;

	public static final int CLAN = 1;

	public static final int CHAT = 2;

	public static final int GROUP = 3;

	public static final int NEWBIE = 4;

	public static final int RAID = 5;

	private static final String FLAG_NAME[] = { "AUCTION", "CLAN", "CHAT",
			"GROUP", "NEWBIE", "RAID" };

	private static final String TRUE_DESCRIPTION[] = {
			"You will receive communication from auctions",
			"You will receive communication from your clan",
			"You will receive chat messages",
			"You will receive group tell messages",
			"You will receive chat from newbies",
			"You will receive get raid communication" };

	private static final String FALSE_DESCRIPTION[] = {
			"You will not receive communication from auctions",
			"You will not receive communication from your clan",
			"You will not receive chat messages",
			"You will not receive group tell messages",
			"You will not receive chat from newbies",
			"You will not receive get raid communication" };

	private boolean mData[] = new boolean[6];

	public ChannelData() {
		for (int index = 0; index < mData.length; index++) {
			mData[index] = true;
		}
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
		StringBuilder sb = new StringBuilder("");
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
