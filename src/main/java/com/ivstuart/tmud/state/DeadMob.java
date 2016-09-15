/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

public class DeadMob {

	private String _id = null;
	private String _repopRoomId = null;
	private long _repopulateTime = 0;

	public DeadMob(String id_, String repopRoomId_, long secondsUntilRepop) {
		_id = id_;
		_repopRoomId = repopRoomId_;
		_repopulateTime = System.currentTimeMillis()
				+ (secondsUntilRepop * 1000);
	}

	public String getID() {
		return _id;
	}

	public String getRepopRoomID() {
		return _repopRoomId;
	}

	public boolean shouldRepopulate() {
		return (System.currentTimeMillis() > _repopulateTime);
	}
}
