/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 04-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.world;

import java.io.Serializable;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ChannelHistory implements Serializable {

	private static final long serialVersionUID = -5656696925007287209L;

	private static final ChannelHistory theHistory = new ChannelHistory();
	private transient Channel auction;
	private transient Channel chat;
	private transient Channel raid;
	private transient Channel newbie;

	private ChannelHistory() {
		auction = new Channel();
		chat = new Channel();
		raid = new Channel();
		newbie = new Channel();
	}

	public static ChannelHistory getInstance() {
		return theHistory;
	}

	/**
	 * @return
	 */
	public Channel getAuction() {
		return auction;
	}

	/**
	 * @return
	 */
	public Channel getChat() {
		return chat;
	}

	/**
	 * @return
	 */
	public Channel getNewbie() {
		return newbie;
	}

	/**
	 * @return
	 */
	public Channel getRaid() {
		return raid;
	}

}
