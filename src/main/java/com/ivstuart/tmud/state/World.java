package com.ivstuart.tmud.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.admin.Ban;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.exceptions.MudException;
import com.ivstuart.tmud.person.Player;

public class World {

	private static final Logger LOGGER = Logger.getLogger(World.class);

	private static Map<String, Tickable> tickers;
	private static Map<String, Zone> _zones;
	private static Map<String, Room> _rooms;
	private static Map<String, Mob> _mobs;
	private static Map<String, Item> _items;
	private static Map<String, Prop> _props;
	private static Map<String, BaseSkill> skills;
	private static Map<String, Spell> spells;
	private static Set<String> _players;
	private static Set<String> _playerNames;

	private static World INSTANCE = new World();

	public static void add(BaseSkill skill) {
		LOGGER.info("Adding skill [ " + skill.getId() + "]");
		skills.put(skill.getId(), skill);
	}

	public static void add(Item item) {
		LOGGER.debug("Adding item [ " + item.getId() + "]");
		_items.put(item.getId(), item);
	}

	public static void add(Mob mob_) {
		LOGGER.info("Adding mob [ " + mob_.getId() + " ]");

		_mobs.put(mob_.getId(), mob_);
	}

	public static void add(Prop prop) {
		LOGGER.info("Adding prop [ " + prop.getId() + "]");
		_props.put(prop.getId(), prop);
	}

	public static void add(Room room) {
		LOGGER.debug("Adding room [ " + room.getId() + "]");
		_rooms.put(room.getId(), room);
	}

	public static void add(Spell spell) {
		LOGGER.info("Adding spell [ " + spell.getId() + "]");
		spells.put(spell.getId(), spell);
	}

	public static void add(Zone zone) {
		LOGGER.debug("Adding zone [ " + zone.getId() + "]");
		_zones.put(zone.getId(), zone);
	}

	public static void addPLayer(Mob character) throws MudException {

		LOGGER.info("Adding player [ " + character.getName() + " ]");

		// Guard
		if (!character.isPlayer()) {
			LOGGER.error("Mob is not a player!");
			throw new MudException("Mob is not a player");
		}

		_players.add(character.getName());

		WorldTime.addTickable(character);

	}

	public static void addTicker(Tickable ticker) {
		LOGGER.info("Adding ticker [ " + ticker.getId() + "]");
		tickers.put(ticker.getId(), ticker);
	}

	public static Prop createProp(String id_) {
		LOGGER.info("Creating prop with id [ " + id_ + "]");

		return _props.get(id_);
	}

	public static BaseSkill getAbility(String name) {
		BaseSkill skill = skills.get(name);

		if (skill != null) {
			return skill;
		}

		return spells.get(name);

	}

	public static Item getItem(String itemId) {
		return _items.get(itemId);
	}

	public static Mob getMob(String name_) {
		return _mobs.get(name_);
	}

	public static Map<String, Mob> getMobs() {
		return _mobs;
	}

	public static Player getPlayer(String playerName) {
		Mob playerMob = _mobs.get(playerName);

		if (playerMob != null) {
			return playerMob.player;
		}

		return null;
	}

	public static Set<String> getPlayerNames() {
		return _playerNames;
	}

	public static Set<String> getPlayers() {
		return _players;
	}

	public static Prop getProp(String id_) {

		return _props.get(id_);
	}

	public static Room getRoom(String id_) {
		return _rooms.get(id_);
	}

	public static BaseSkill getSkill(String name) {
		return skills.get(name);

	}

	public static Map<String, BaseSkill> getSkills() {
		return skills;
	}

	public static Spell getSpell(String name) {

		return spells.get(name);

	}

	/**
	 * TODO work out if I should expose reference. I should make the list
	 * immutable once loaded.
	 * 
	 * @return
	 */
	public static Map<String, Spell> getSpells() {
		return spells;
	}

	public static boolean isOnline(String name_) {
		return _players.contains(name_);
	}

	public static void removePlayer(Player player) {
		LOGGER.info("Creating guard mob with id [ " + player.getName() + "]");

		_playerNames.remove(player.getName());
		_players.remove(player.getName());

	}
	
	public void addToWorld(Object object) {

		if (object instanceof Room) {
			World.add((Room) object);
			return;
		}

		if (object instanceof GuardMob) {
			World.add((GuardMob) object);
			return;
		}

		if (object instanceof Mob) {
			World.add((Mob) object);
			return;
		}

		if (object instanceof Item) {
			World.add((Item) object);
			return;
		}

		if (object instanceof Zone) {
			World.add((Zone) object);
			return;
		}

		if (object instanceof Spell) {
			World.add((Spell) object);
			return;
		}

		if (object instanceof BaseSkill) {
			World.add((BaseSkill) object);
			return;
		}

		if (object instanceof Prop) {
			World.add((Prop) object);
			return;
		}
		
		if (object != null) {
			LOGGER.warn("Unknow object type ["+object.getClass().getSimpleName()+"]");
		}
		else {
			LOGGER.warn("Object of null reference attempted to add to the world!");
		}
		// TODO throw new IllegalArgumentException("Unknow object type ["+object.getClass().getSimpleName()+"]");

	}

	private World() {
		tickers = new HashMap<String, Tickable>();
		_zones = new HashMap<String, Zone>();
		_rooms = new HashMap<String, Room>();
		_mobs = new HashMap<String, Mob>();
		_items = new HashMap<String, Item>();
		skills = new HashMap<String, BaseSkill>();
		spells = new HashMap<String, Spell>();
		_players = new HashSet<String>();
		_playerNames = new HashSet<String>(); // Reserved name for character
												// creation
		_props = new HashMap<String, Prop>();

		// Initialise banned list of player names if it exists.
		Ban.init();
		
		startTime();
	}

	private void startTime() {
		Thread worldTimeThread = new Thread(WorldTime.getInstance());
		worldTimeThread.setName("WorldTime-" + worldTimeThread.getId());
		worldTimeThread.setDaemon(true);
		worldTimeThread.start();

		LOGGER.info("WorldTime running [ "
				+ WorldTime.getInstance().isRunning() + " ]");
	}

	
	public static World getInstance() {
		return INSTANCE;
	}


    public static boolean isAdmin(String name) {
    	// TODO have a resource file.
    	List adminNames = new ArrayList();
		adminNames.add("Ivan");
		return adminNames.contains(name);
    }
}
