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
