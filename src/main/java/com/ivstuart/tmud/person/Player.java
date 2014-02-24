package com.ivstuart.tmud.person;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.person.config.ConfigData;
import com.ivstuart.tmud.server.Connection;
import com.ivstuart.tmud.server.Output;
import com.ivstuart.tmud.state.Attributes;
import com.ivstuart.tmud.state.Mob;

public class Player implements Serializable, Nameable {

	private static final Logger LOGGER = Logger.getLogger(Player.class);

	private static final long serialVersionUID = 1L;

	private Mob mob;

	private Config config;

	private PlayerData playerData;

	protected Attributes attributes;

	private transient Connection conn;

	private boolean admin = false;
	
	public void setSnooper(Mob snooper) {
		this.snooper = snooper;
	}

	private Mob snooper;

	public Mob getSnooper() {
		return snooper;
	}

	public Player() {
		playerData = new PlayerData();
		config = new Config();
	}

	public boolean checkIfLeveled() {
		if (playerData.getToLevelXp() < 1) {

			playerData.incrementLevel();

			this.level();

			return true;
		}
		return false;
	}

	public void disconnect() {
		conn.disconnect();
	}

	public Attributes getAttributes() {
		return attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.Player#getConfig()
	 */
	public Config getConfig() {
		return config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.Player#getData()
	 */
	public PlayerData getData() {
		return playerData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.Player#getCharacter()
	 */
	public Mob getMob() {
		return mob;
	}

	@Override
	public String getName() {
		return mob.getName();
	}

	private void level() {
		out("-->Your overall power and learning capacities have increased!<--");

		LOGGER.info("Player " + getName() + " has gained a level");

		// STRENGTH, CONSTITUTION, INTELLIGENCE, DEXTERITY, WISDOM;

		int dHP = attributes.getCON().getMaximum() + (int) (Math.random() * 10);

		mob.getHp().increaseMaximum(dHP);
		mob.getHp().restore();

		int dMv = attributes.getDEX().getMaximum() + (int) (Math.random() * 4);

		mob.getMv().increaseMaximum(dMv);
		mob.getMv().restore();

		int dMana = (attributes.getINT().getMaximum()
				+ attributes.getWIS().getMaximum() + (int) (Math.random() * 9)) / 3;

		mob.getMana().addMaximum(dMana);

		out("You gained " + dHP + " hitpoints, " + dMv + " moves and [" + dMana
				+ "] mana.");

		out("You feel restored!");

		// TODO decide about this.
		checkIfLeveled();
	}

	public void out(String message) {
		message = Output.getString(message,
				config.getConfigData().is(ConfigData.ANSI));

		LOGGER.debug(getName()+" output ["+message+"]");

		if (conn != null) {
			conn.out(message);
			return;
		}

		LOGGER.info("Character " + getName() + " has lost their connection!");

	}

	public void setAttributes(int[] att) {
		attributes = new Attributes(att);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.Player#setConfig(person.Config)
	 */
	public void setConfig(Config config) {
		this.config = config;
	}

	public void setConnection(Connection connection) {
		conn = connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.Player#setData(person.PlayerData)
	 */
	public void setData(PlayerData data) {
		this.playerData = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.Player#setCharacter(person.Mob)
	 */
	public void setMob(Mob me) {
		this.mob = me;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean b) {
		admin = b;
	}

}
