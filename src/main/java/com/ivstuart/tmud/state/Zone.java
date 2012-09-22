package com.ivstuart.tmud.state;

import com.ivstuart.tmud.state.util.EntityProvider;

/**
 * Used to construct state during world uploading post loading in the various
 * objects
 * 
 * @author Ivan Stuart
 * 
 */
public class Zone extends BasicThing {

	private static final long serialVersionUID = 1372416598506829326L;

	protected int _lifespan;// 300

	protected int _reset; // enum?

	public Zone() {

	}

	public void setGive(String give_) {

	}

	public void setItemInRoom(String itemAndRoom_) {
		String elements[] = itemAndRoom_.split(" ");
		Item item = EntityProvider.createItem(elements[0]);
		Room room = World.getRoom(elements[1]);
		room.add(item);
	}

	public void setLifespan(String lifespan_) {
		_lifespan = Integer.parseInt(lifespan_);
	}

	public void setMobInRoom(String mobAndRoom_) {
		String elements[] = mobAndRoom_.split(" ");

		Mob mob = EntityProvider.createMob(elements[0], elements[1]);

		Room room = World.getRoom(elements[1]);

		room.add(mob);
	}

	public void setName(String name_) {
		// TODO
	}

	public void setPut(String item_) {

	}

	public void setReset(Integer reset_) {
		_reset = reset_;
	}
}
