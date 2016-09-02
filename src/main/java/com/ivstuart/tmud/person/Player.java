package com.ivstuart.tmud.person;

import com.ivstuart.tmud.constants.Profession;
import com.ivstuart.tmud.person.carried.Inventory;
import com.ivstuart.tmud.person.config.ConfigData;
import com.ivstuart.tmud.server.Connection;
import com.ivstuart.tmud.server.Output;
import com.ivstuart.tmud.state.Attributes;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Serializable, Nameable {

	private static final Logger LOGGER = LogManager.getLogger();

	private static final long serialVersionUID = 1L;
	protected Attributes attributes;
	private Mob mob;
	private Config config;
	private PlayerData playerData;
	private transient Connection connection;

	private boolean admin = false;

	private Map<String,String> alias; // Limit to 30

	private Mob snooper;

	private transient List<Mob> group;

	private Inventory bank;
	private Guilds guilds;
	private Profession profession;

	public Player() {
		playerData = new PlayerData();
		config = new Config();
		alias = new HashMap<>();
		bank = new Inventory();
		guilds = new Guilds();
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public Guilds getGuilds() {
		return guilds;
	}

	public List<Mob> getGroup() {
		return group;
	}

	public void setGroup(List<Mob> group) {
		this.group = group;
	}

	public Mob getSnooper() {
		return snooper;
	}

	public void setSnooper(Mob snooper) {
		this.snooper = snooper;
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
		connection.disconnect();
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(int[] att) {
		attributes = new Attributes(att);

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
	 * @see person.Player#setConfig(person.Config)
	 */
	public void setConfig(Config config) {
		this.config = config;
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
	 * @see person.Player#setData(person.PlayerData)
	 */
	public void setData(PlayerData data) {
		this.playerData = data;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see person.Player#getCharacter()
	 */
	public Mob getMob() {
		return mob;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see person.Player#setCharacter(person.Mob)
	 */
	public void setMob(Mob me) {
		this.mob = me;
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

		int dMv = (attributes.getDEX().getMaximum() + (int) (Math.random() * 5))/ 5;

		dMv += mob.getRace().getMovement();

		mob.getMv().increaseMaximum(dMv);
		mob.getMv().restore();

		int dMana = (attributes.getINT().getMaximum()
				+ attributes.getWIS().getMaximum() + (int) (Math.random() * 9)) / 9;

		mob.getMana().addMaximumAndRestore(dMana);

		out("You gained " + dHP + " hitpoints, " + dMv + " moves and [" + dMana
				+ "] mana.");

		out("You feel restored!");

		checkIfLeveled();
	}

	public void out(String message) {
		message = Output.getString(message,
				config.getConfigData().is(ConfigData.ANSI));

		LOGGER.debug(getName()+" output ["+message+"]");

		if (connection != null) {
			connection.out(message);
			return;
		}

		LOGGER.info("Character " + getName() + " has lost their connection!");

	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean b) {
		admin = b;
	}

	public void addAlias(String word, String word1) {
		if (alias.size() > 30) {
			out("You are at the limit for alias's please remove one and then you can add another one");
			return;
		}
		alias.put(word,word1);
	}

	public void removeAlias(String word) {
		alias.remove(word);
	}

	public void showAlias() {
		this.out(alias.toString());
	}

	public String applyAlias(String input) {
		if (alias == null) {
			return input;
		}
		if (input.startsWith("alias")) {
			return input;
		}
		for (Map.Entry<String,String> set : alias.entrySet()) {
			input = input.replaceFirst("^"+set.getKey(),set.getValue());
		}
		return input;
	}

    public Connection getConnection() {
        return connection;
    }

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Inventory getBank() {
		if (bank == null) {
			bank = new Inventory();
		}
		return bank;
	}

	public int getAPB() {

		int apb = 1 + this.getAttributes().getSTR().getValue() / 5;

		apb += mob.getEquipment().getAPB();

		return apb;
	}
}
