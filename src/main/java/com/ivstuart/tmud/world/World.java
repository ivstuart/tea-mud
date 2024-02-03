/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.world;

import com.ivstuart.tmud.command.admin.AddAdmin;
import com.ivstuart.tmud.command.admin.Ban;
import com.ivstuart.tmud.command.communication.AuctionItem;
import com.ivstuart.tmud.command.misc.ForcedQuit;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.exceptions.MudException;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.config.ChannelEnum;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.Prop;
import com.ivstuart.tmud.state.mobs.GuardMob;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.places.RoomBuilder;
import com.ivstuart.tmud.state.places.RoomLocation;
import com.ivstuart.tmud.state.places.Zone;
import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.state.setup.ItemProvider;
import com.ivstuart.tmud.state.setup.RaceProvider;
import com.ivstuart.tmud.state.setup.SkillsProvider;
import com.ivstuart.tmud.state.setup.SpellProvider;
import com.ivstuart.tmud.state.skills.BaseSkill;
import com.ivstuart.tmud.state.skills.Spell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class World {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final World INSTANCE = new World();
    private static Map<String, Tickable> tickers;
    private static Map<String, Zone> zones;
    private static Map<RoomLocation, Room> rooms;
    private static Map<String, Mob> mobs;
    private static Map<String, Item> items;
    private static Map<String, Prop> props;
    private static Map<String, BaseSkill> skills;
    private static Map<String, Spell> spells;
    private static Set<String> players; // Names in lowercase.
    private static ScheduledExecutorService scheduledExecutorService;

    private static List<Race> races;
    private static Map<String, AuctionItem> auction;
    private static WeatherSky weather;
    private static MudStats mudStats;

    private World() {
        tickers = new HashMap<>();
        zones = new HashMap<>();
        rooms = new HashMap<>();
        mobs = new HashMap<>();
        items = new HashMap<>();
        skills = new HashMap<>();
        spells = new HashMap<>();
        players = new HashSet<>();
        props = new HashMap<>();
        races = new ArrayList<>();
        auction = new HashMap<>();

        // Initialise banned list of player names if it exists.
        Ban.init();
        AddAdmin.init();
        Boards.init();
        PostalSystem.init();
        Clans.init();

        // Code as config
        RaceProvider.load();
        SkillsProvider.load();
        SpellProvider.load();
        ItemProvider.load();

        mudStats = MudStats.init();

        if (mudStats == null) {
            mudStats = new MudStats();
        }

        startTime();

        weather = WeatherSky.CLOUDLESS;
    }

    public static WeatherSky getWeather() {
        return weather;
    }

    public static void setWeather(WeatherSky weather) {
        World.weather = weather;
    }

    public static void add(BaseSkill skill) {
        LOGGER.info("Adding skill [" + skill.getId() + "]");
        skills.put(skill.getId(), skill);
    }

    public static void add(Item item) {
        LOGGER.debug("Adding item [" + item.getId() + "]");
        items.put(item.getId(), item);
    }

    public static void add(Mob mob_) {
        LOGGER.info("Adding mob [" + mob_.getId() + " ]");

        mobs.put(mob_.getId(), mob_);
    }

    public static void add(Prop prop) {
        LOGGER.info("Adding prop [" + prop.getId() + "]");
        props.put(prop.getId(), prop);
    }

    public static void add(RoomLocation roomLocation,Room room) {

        if(rooms.containsKey(roomLocation)) {
            LOGGER.debug("Failed to add room which already exists [" + roomLocation + "]:["+room.getRoomLocation());
            return;
        }

        LOGGER.debug("Adding room [" + roomLocation + "]:["+room.getRoomLocation());
        rooms.put(roomLocation,room);
    }

    public static void add(Spell spell) {
        LOGGER.info("Adding spell [" + spell.getId() + "]");
        spells.put(spell.getId(), spell);
    }

    public static void add(Zone zone) {
        LOGGER.debug("Adding zone [" + zone.getId() + "]");
        zones.put(zone.getId(), zone);
    }

    public static void addPlayer(Mob character) throws MudException {

        LOGGER.info("Adding player [" + character.getName() + " ]");

        // Guard
        if (!character.isPlayer()) {
            LOGGER.error("Mob is not a player!");
            throw new MudException("Mob is not a player");
        }

        players.add(character.getName().toLowerCase());

        mobs.put(character.getId().toLowerCase(), character);

        WorldTime.addTickable(character);

    }

    public static void addTicker(Tickable ticker) {
        LOGGER.info("Adding ticker [" + ticker.getId() + "]");
        tickers.put(ticker.getId(), ticker);
    }

    public static Prop createProp(String id_) {
        LOGGER.info("Creating prop with id [" + id_ + "]");

        return props.get(id_);
    }

    public static BaseSkill getAbility(String name) {
        BaseSkill skill = skills.get(name);

        if (skill != null) {
            return skill;
        }

        return spells.get(name);

    }

    public static Item getItem(String itemId) {
        return items.get(itemId);
    }

    public static Mob getMob(String name_) {
        return mobs.get(name_);
    }

    public static Map<String, Mob> getMobs() {
        return mobs;
    }

    public static Player getPlayer(String playerName) {
        Mob playerMob = mobs.get(playerName);

        if (playerMob != null) {
            return playerMob.getPlayer();
        }

        return null;
    }

    public static Set<String> getPlayers() {
        return players;
    }

    public static Prop getProp(String id_) {

        return props.get(id_);
    }

    public static Room getRoom(String id_) {
        return rooms.get(id_);
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
        return players.contains(name_);
    }

    public static void removePlayer(Player player) {
        LOGGER.info("Removing player with id [" + player.getName() + "]");

        players.remove(player.getName().toLowerCase());

        tickers.remove(player.getName());

        mobs.remove(player.getName());

        WorldTime.removeTickables(player.getMob());


    }


    public static void add(Race race) {
        races.add(race);
    }

    public static World getInstance() {
        return INSTANCE;
    }

    public static boolean isAdmin(String name) {
        return AddAdmin.isAdmin(name);
    }

    public static void out(String msg) {
        for (String player : players) {
            Mob aPlayer = mobs.get(player.toLowerCase());
            aPlayer.out(msg);
        }
    }

    public static void out(String msg, boolean good) {
        for (String player : players) {
            Mob aPlayer = mobs.get(player.toLowerCase());
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
        Mob playerMob = mobs.get(name.toLowerCase());
        new ForcedQuit().execute(playerMob, null);
    }

    public static RoomLocation getPortalLocation(Mob defender) {
        return getPortal(defender.isGood()).getRoomLocation();
    }

    public static void registerAuction(Mob seller, AuctionItem auctionItem) {
        auction.put(seller.getName(), auctionItem);
    }

    public static AuctionItem getAuction(String sellerName) {
        return auction.get(sellerName);
    }

    public static void shutdownAuctions() {
        for (AuctionItem auctionItem : auction.values()) {
            auctionItem.finishAuction();
        }
    }

    public static AuctionItem removeAuction(Mob seller) {
        return auction.remove(seller.getName());
    }

    public static void out(String msg, boolean good, ChannelEnum channelEnum) {
        for (String player : players) {
            Mob aPlayer = mobs.get(player.toLowerCase());
            if (aPlayer.isGood() == good) {
                if (aPlayer.getPlayer().getConfig().getChannelData().isFlagSet(channelEnum)) {
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

    public static Object remove(String input) {
        Object object;
        object = mobs.remove(input);
        if (object != null) {
            return object;
        }
        object = rooms.remove(input);
        if (object != null) {
            return object;
        }
        object = items.remove(input);
        if (object != null) {
            return object;
        }
        object = zones.remove(input);
        if (object != null) {
            return object;
        }
        object = props.remove(input);
        if (object != null) {
            return object;
        }
        object = spells.remove(input);
        if (object != null) {
            return object;
        }
        object = skills.remove(input);
        return object;
    }

    public static boolean isPlayer(String playerInput) {
        return true;
    }

    public static MudStats getMudStats() {
        return mudStats;
    }

    public static List<Race> getRaces() {
        return races;
    }

    public static void addToWorld(Object object) {

//        if (object instanceof Room) {
//            World.add(new RoomLocation(0,0,0), (Room) object);
//            return;
//        }

        if (object instanceof GuardMob) {
            World.add((GuardMob) object);
            return;
        }

        if (object instanceof Mob) {
            Mob mob = (Mob)object;
            if (mob.getTickers() != null) {
                WorldTime.addTickable(mob);
            }
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
            LOGGER.warn("Unknown object type [" + object.getClass().getSimpleName() + "]");
        } else {
            LOGGER.warn("Object of null reference attempted to add to the world!");
        }

    }

    public static Room getRoom(RoomLocation roomLocation) {
        // LOGGER.debug("Getting room "+roomLocation);
        return rooms.get(roomLocation);
    }

    public static int getNumberOfRooms() {
        return rooms.size();
    }

    public static void add(Room root) {
        rooms.put(root.getRoomLocation(), root);
    }

    private void startTime() {

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(WorldTime.getInstance(), 0, 100, TimeUnit.MILLISECONDS);

        LOGGER.info("WorldTime running [ "
                + (!scheduledFuture.isCancelled()) + " ]");

        scheduledExecutorService.scheduleAtFixedRate(new Weather(), 0, 30, TimeUnit.MINUTES);
    }

    // Yes I know I am not using a map.gson here. Loading in order
    public Race getRace(int id) {

        if (races.isEmpty()) {
            return null;
        }

        if (id == 0) {
            id = 1;
        } // Default to human when no race set.
        return races.get(id - 1);
    }

    public static Room getPortal() {
        return getPortal(true);
    }

    public static Room getPortal(boolean alignment){

        Room portal;

        if (alignment) {
            portal = rooms.get(RoomLocation.PORTAL_GOOD);
        }
        else {
            portal = rooms.get(RoomLocation.PORTAL_EVIL);
        }

        if (portal == null) {
            portal = new Room(new RoomLocation(0,0,0));
            World.add(portal);
        }

        return portal;
    }

    public static Map<RoomLocation, Room> getRooms() {
        return rooms;
    }
}
