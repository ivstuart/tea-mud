package com.ivstuart.tmud.state;

import java.util.ArrayList;
import java.util.List;

import com.ivstuart.tmud.common.Tickable;

public class WanderOld implements Tickable {

	private List<String> beenTo = new ArrayList<String>();
	private int maxSize;

	public WanderOld(Mob mob) {
		// TODO Auto-generated constructor stub
	}

	public boolean addBeenTo(String roomId) {
		int index = beenTo.indexOf(roomId);

		if (index == -1) {
			beenTo.add(roomId);
		} else {
			beenTo = beenTo.subList(0, index + 1);
		}

		return (beenTo.size() > maxSize);
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		// MoveManager.random(mob);
	}

}
