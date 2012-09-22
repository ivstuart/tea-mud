package com.ivstuart.tmud.state.dao;

import com.ivstuart.tmud.state.Door;

public class DoorDAO {

	private Door door;

	private String room;

	private String exit;

	public Door getDoor() {
		return door;
	}

	public String getExit() {
		return exit;
	}

	public String getRoom() {
		return room;
	}

	public void setDoor(Door door) {
		this.door = door;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "DoorDAO [door=" + door + ", room=" + room + ", exit=" + exit
				+ "]";
	}
}
