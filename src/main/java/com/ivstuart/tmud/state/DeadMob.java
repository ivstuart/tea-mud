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
