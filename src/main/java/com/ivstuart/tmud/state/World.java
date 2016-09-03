package com.ivstuart.tmud.state;

import com.ivstuart.tmud.command.admin.AddAdmin;
import com.ivstuart.tmud.command.admin.Ban;
import com.ivstuart.tmud.command.communication.AuctionItem;
import com.ivstuart.tmud.command.misc.ForcedQuit;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.exceptions.MudException;
import com.ivstuart.tmud.person.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class World {

	private static final Logger LOGGER = LogManager.getLogger();

	private static Map<String, Tickable> tickers;
	private static Map<String, Zone> _zones;
	private static Map<String, Room> _rooms;
	private static Map<String, Mob> _mobs;
	private static Map<String, Item> _items;
	private static Map<String, Prop> _props;
	private static Map<String, BaseSkill> skills;
	private static Map<String, Spell> spells;
	private static Set<String> _players; // Names in lowercase.

	private static World INSTANCE = new World();

	private static ScheduledExecutorService scheduledExecutorService;

	private static List<Race> races;
	private static Map<String, AuctionItem> auction;

	private World() {
		tickers = new HashMap<>();
		_zones = new HashMap<>();
		_rooms = new HashMap<>();
		_mobs = new HashMap<>();
		_items = new HashMap<>();
		skills = new HashMap<>();
		spells = new HashMap<>();
		_players = new HashSet<>();
		_props = new HashMap<>();
		races = new ArrayList<>();
		auction = new HashMap<>();

        // Initialise banned list of player names if it exists.
        Ban.init();
        AddAdmin.init();

        startTime();
    }

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

	public static void addPlayer(Mob character) throws MudException {

		LOGGER.info("Adding player [ " + character.getName() + " ]");

		// Guard
		if (!character.isPlayer()) {
			LOGGER.error("Mob is not a player!");
			throw new MudException("Mob is not a player");
		}

		_players.add(character.getName().toLowerCase());

		_mobs.put(character.getId().toLowerCase(), character);

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

	public static Map<String, Spell> getSpells() {
		return spells;
	}

	public static boolean isOnline(String name_) {
		return _players.contains(name_);
	}

	public static void removePlayer(Player player) {
		LOGGER.info("Removing player with id [ " + player.getName() + "]");

		_players.remove(player.getName());

	}

	public static Map<String,Room> getRooms() {
		return _rooms;
	}

    private static void add(Race race) {
        races.add(race);
    }

    public static World getInstance() {
        return INSTANCE;
    }

    public static boolean isAdmin(String name) {
        return AddAdmin.isAdmin(name);
    }

    public static void out(String msg) {
        for (String player : _players) {
            Mob aPlayer = _mobs.get(player.toLowerCase());
            aPlayer.out(msg);
        }
    }

    public static void out(String msg, boolean good) {
        for (String player : _players) {
            Mob aPlayer = _mobs.get(player.toLowerCase());
            if (aPlayer.isGood() == good) {
                aPlayer.out(msg);
            }
        }
    }

    public static void shutdown() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    public static void kickout(String name) {
        Mob playerMob = _mobs.get(name.toLowerCase());
        new ForcedQuit().execute(playerMob, null);
    }

    public static Room getPortal(Mob defender) {
        if (defender.isGood()) {
            return World.getRoom("R-P2");
        } else {
            return World.getRoom("R-P1");
        }
    }

	public static void registerAuction(Mob seller, AuctionItem auctionItem) {
		auction.put(seller.getName(), auctionItem);
	}

	public static AuctionItem getAuction(String sellerName) {
		return auction.get(sellerName);
	}

	public static AuctionItem removeAuction(Mob seller) {
		return auction.remove(seller.getName());
	}

	public static void out(String msg, boolean good, int channelData) {
		for (String player : _players) {
			Mob aPlayer = _mobs.get(player.toLowerCase());
			if (aPlayer.isGood() == good) {
				if (aPlayer.getPlayer().getConfig().getChannelData().is(channelData)) {
					aPlayer.out(msg);
				}
			}
		}
	}

	public static Room getDonateRoom(Mob mob) {
		if (mob.isGood()) {
			return World.getRoom("Z0-:2:0:-1");
		} else {
			return World.getRoom("Z6-:2:0:-1");
		}
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

		if (object instanceof Race) {
			World.add((Race) object);
			return;
		}

		if (object instanceof RoomBuilder) {
			// This is fine.
			return;
		}

        if (object != null) {
			LOGGER.warn("Unknow object type ["+object.getClass().getSimpleName()+"]");
		}
		else {
			LOGGER.warn("Object of null reference attempted to add to the world!");
		}

	}

	private void startTime() {

		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(WorldTime.getInstance(),0,100, TimeUnit.MILLISECONDS);

		LOGGER.info("WorldTime running [ "
				+ (!scheduledFuture.isCancelled()) + " ]");
	}

	// Yes I know I am not using a map here. Loading in order
	public Race getRace(int id) {
		if (id == 0) {
			id = 1;
		} // Default to human when no race set.
		return races.get(id - 1);
	}
}
