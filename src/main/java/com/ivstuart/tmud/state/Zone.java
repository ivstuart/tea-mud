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

import com.ivstuart.tmud.state.util.EntityProvider;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Used to construct state during world uploading post loading in the various
 * objects
 * 
 * @author Ivan Stuart
 * 
 */
public class Zone extends BasicThing {

	private static final Logger LOGGER = LogManager.getLogger();

	private static final long serialVersionUID = 1372416598506829326L;

	protected int _lifespan;// 300

	protected int _reset; // enum?

	private String name;

	public Zone() {

	}

	/**
	 * Mob then item space seperated then load percentage for item
	 *
	 * @param give_
	 */
	public void setGive(String give_) {
		String elements[] = give_.split(" ");

		if (elements.length < 2) {
			LOGGER.error("Give needs to be [mob] [item] <load percentage>");
			return;
		}
		Mob mob = World.getMob(elements[0]);
		Item item = EntityProvider.createItem(elements[1]);
		mob.getInventory().add(item); // Gives to template mob only

		if (elements.length == 3) {
			item.setLoadPercentage(Integer.parseInt(elements[2]));
		}
	}

	public void setItemInRoom(String itemAndRoom_) {
		String elements[] = itemAndRoom_.split(" ");
		Item item = EntityProvider.createItem(elements[0]);
		Room room = World.getRoom(elements[1]);
		room.add(item);
	}

	public void setGiveItem(String give_) {
		String elements[] = give_.split(" ");

		if (elements.length < 3) {
			LOGGER.error("Give needs to be [mob] [room] [item] <load percentage>");
			return;
		}

		Mob mob = World.getRoom(elements[1]).getMob(elements[0]);

		if (mob == null) {
			LOGGER.error("Mob was null for id: "+elements[0]);
			return;
		}

		Item item = EntityProvider.createItem(elements[2]);

		if (item == null) {
			LOGGER.error("Item was null for id: "+elements[2]);
			return;
		}

		if (elements.length == 4) {
			item.setLoadPercentage(Integer.parseInt(elements[3]));
		}

		if (item.isLoaded()) {
			mob.getInventory().add(item); // Gives to room mob only.
		}
	}


	public void setLifespan(String lifespan_) {
		_lifespan = Integer.parseInt(lifespan_);
	}

	public void setMobInRoom(String mobAndRoom_) {
		String elements[] = mobAndRoom_.split(" ");

		//LOGGER.debug("Elements :"+elements[0]+" "+elements[1]);

		Mob mob = EntityProvider.createMob(elements[0], elements[1]);

		if (mob == null) {
			LOGGER.error("Room id:" + elements[1] + " mob null for:" + elements[0]);
			return;
		}

		Room room = World.getRoom(elements[1]);

		if (room == null) {
			LOGGER.error("Room id:"+elements[1]+" null for mob:"+elements[0]);
		}
		// LOGGER.debug("Adding mob "+mob.getName()+" to room "+room.getId());

		room.add(mob);
	}

	public void setPropInRoom(String propInRoom) {
		String elements[] = propInRoom.split(" ");

		//LOGGER.debug("Elements :"+elements[0]+" "+elements[1]);

		Prop prop = EntityProvider.createProp(elements[0]);

		Room room = World.getRoom(elements[1]);

		// LOGGER.debug("Adding mob "+mob.getName()+" to room "+room.getId());

		room.add(prop);
	}

	public void setName(String name_) {
		this.name = name_;
	}

	public void setReset(Integer reset_) {
		_reset = reset_;
	}
}
