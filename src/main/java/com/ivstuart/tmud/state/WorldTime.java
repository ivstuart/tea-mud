package com.ivstuart.tmud.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.state.util.EntityProvider;

public class WorldTime implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(WorldTime.class);

	private static List<Mob> fighting;

	// TODO Should this not just be handled by the mobs list?
	private static List<DeadMob> deadMobs;

	private static List<Tickable> tickables;

	private static boolean _running = false;

	private static final WorldTime INSTANCE = new WorldTime();

	public static void addFighting(Mob mob_) {
		// TODO should I have a Set<Fight> for this instead?
		// if (!mob_.getFight().isFighting()) {}
		fighting.add(mob_);
	}

	public static void addTickable(Tickable item_) {
		tickables.add(item_);
	}

	/**
	 * @Admin
	 * @return
	 * 
	 */
	public static List<Mob> getFighting() {
		return fighting;
	}

	/**
	 * Only World and TestHelper are allowed to get an instance of WorldTime
	 * @return
	 */
	public static WorldTime getInstance() {
		return INSTANCE;
	}

	public static String getTime() {
		// TODO This needs to be updated
		long time = System.currentTimeMillis() / 1000;
		return "Time is : " + time;
	}

	public static void init() {

		fighting = new ArrayList<Mob>();
		tickables = new ArrayList<Tickable>();
		deadMobs = new ArrayList<DeadMob>();
	}

	public static boolean removeFighting(Mob mob_) {
		return fighting.remove(mob_);
	}

	public static boolean removeItem(Item item_) {
		return tickables.remove(item_);
	}

	public static void scheduleMobForRepopulation(Mob mob_) {
		LOGGER.debug("scheduleMobForRepopulation with id [ " + mob_.getId()
				+ " ]");

		DeadMob dead = new DeadMob(mob_.getId(), mob_.getRepopRoomId(), 10);

		deadMobs.add(dead);
	}

	public static void setRunning() {
		_running = true;
	}

	private WorldTime() {
		WorldTime.init();
	}


	public boolean isRunning() {
		return _running;
	}

	public void repopulateMobs() {
		for (Iterator<DeadMob> iter = deadMobs.iterator(); iter.hasNext();) {
			DeadMob deadMob = iter.next();
			if (deadMob.shouldRepopulate()) {

				iter.remove();
				Mob mob = EntityProvider.createMob(deadMob.getID(),
						deadMob.getRepopRoomID());

				Room repopRoom = World.getRoom(deadMob.getRepopRoomID());

				repopRoom.add(mob);
			}
		}
	}

	public void resolveCombat() {

		// LOGGER.debug("resolveCombat");

		if (fighting.isEmpty()) {
			return;
		}

		ListIterator<Mob> fightingIter = fighting.listIterator();
		while (fightingIter.hasNext()) {
			Mob mob = fightingIter.next();
			if (mob != null) {
				Fight fight = mob.getFight();
				if (fight.isFighting()) {
					fight.resolveCombat();
				} else {
					LOGGER.debug("removing from fighting [ " + mob.getName()
							+ " ]");

					fightingIter.remove();
				}
			}

		}

	}

	@Override
	public void run() {
		LOGGER.info("Starting the world time");
		int counter = 0;
		setRunning();
		while (_running) {
			counter = ++counter % 150;
			if (counter == 1) {
				// Also need to decrease expiry of affects
				sendHeartBeat();
				repopulateMobs();
			}
			try {
				// Game speed - maybe set this in a config setting somewhere.
				Thread.sleep(100);

				resolveCombat();
			} catch (InterruptedException e) {

				LOGGER.error("Problem during running",e);
			}
		}
		LOGGER.info("Finnished the world time");

	}

	public void sendHeartBeat() {

		for (Tickable tickable : tickables) {
			// TODO add exception handling
			tickable.tick();
		}

	}

	public void tickWithCombat() {
		sendHeartBeat();
		repopulateMobs();
		resolveCombat();
	}
	

}
