package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Msgable;
import com.ivstuart.tmud.person.carried.Inventory;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.util.EntityProvider;
import com.ivstuart.tmud.state.util.RoomManager;
import com.ivstuart.tmud.utils.MudArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Room extends BasicThing implements Msgable {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final long serialVersionUID = 1L;

    private transient List<Track> tracks;

    private String _type;

    private MudArrayList<Prop> _props;

    private MudArrayList<Exit> _exits;

    private MudArrayList<Mob> _mobs;

    private Inventory _items;

    private boolean isRegen;
    private boolean isUnderWater;
    private boolean isDark;
    private boolean isWater;
    private boolean isFlying;
    // DARK, DEATH, NO_MOB, INDOORS, PEACEFUL, SOUND_PROOF, NO_TRACK, NO_MAGIC, TUNNEL, PRIVATE, GOD_ROOM, HOUSE
    private boolean isDeath;
    private boolean isNoMob;
    private boolean isIndoors;
    private boolean isPeaceful;
    private boolean isSoundProof;
    private boolean isNoTrack;
    private boolean isNoMagic;
    private boolean isTunnel;
    private boolean isPrivate;
    private boolean isGodRoom;
    private boolean isHouse;
    private boolean isClimb;

    public Room() {
        initRoom();
    }

    public Room(Room room) {
        super(room);
        initRoom();
        this.isRegen = room.isRegen;
        this.isUnderWater = room.isUnderWater;
        this.isDark = room.isDark;
        this.isWater = room.isWater;
        this.isFlying = room.isFlying;
        this.isDeath = room.isDeath;
        this.isNoMob = room.isNoMob;
        this.isIndoors = room.isIndoors;
        this.isPeaceful = room.isPeaceful;
        this.isSoundProof = room.isSoundProof;
        this.isNoTrack = room.isNoTrack;
        this.isNoMagic = room.isNoMagic;
        this.isTunnel = room.isTunnel;
        this.isPrivate = room.isPrivate;
        this.isGodRoom = room.isGodRoom;
        this.isHouse = room.isHouse;
        this.isClimb = room.isClimb;
    }

    public boolean isClimb() {
        return isClimb;
    }

    public void setClimb(boolean climb) {
        isClimb = climb;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    public boolean isNoMob() {
        return isNoMob;
    }

    public void setNoMob(boolean noMob) {
        isNoMob = noMob;
    }

    public boolean isIndoors() {
        return isIndoors;
    }

    public void setIndoors(boolean indoors) {
        isIndoors = indoors;
    }

    public boolean isPeaceful() {
        return isPeaceful;
    }

    public void setPeaceful(boolean peaceful) {
        isPeaceful = peaceful;
    }

    public boolean isSoundProof() {
        return isSoundProof;
    }

    public void setSoundProof(boolean soundProof) {
        isSoundProof = soundProof;
    }

    public boolean isNoTrack() {
        return isNoTrack;
    }

    public void setNoTrack(boolean noTrack) {
        isNoTrack = noTrack;
    }

    public boolean isNoMagic() {
        return isNoMagic;
    }

    public void setNoMagic(boolean noMagic) {
        isNoMagic = noMagic;
    }

    public boolean isTunnel() {
        return isTunnel;
    }

    public void setTunnel(boolean tunnel) {
        isTunnel = tunnel;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isGodRoom() {
        return isGodRoom;
    }

    public void setGodRoom(boolean godRoom) {
        isGodRoom = godRoom;
    }

    public boolean isHouse() {
        return isHouse;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }

    public boolean isDark() {
        return isDark;
    }

    public void setDark(boolean dark) {
        isDark = dark;
    }

    public boolean isWater() {
        return isWater;
    }

    public void setWater(boolean water) {
        isWater = water;
    }

    private void initRoom() {
        _props = new MudArrayList<Prop>();
        _exits = new MudArrayList<Exit>();
        _mobs = new MudArrayList<Mob>(true); // matching part of name
        tracks = new ArrayList<Track>(0);
        isRegen = false;
        isUnderWater = false;
    }

    public void add(Exit exit_) {
        String exitString = exit_.getId();
        if (_exits.get(exitString) != null) {
            // This is normal now during to building paths that overlap.
            // LOGGER.warn("Adding a duplicate exit to a room !");
            return;
        }
        _exits.add(exit_);
    }

    public void add(Item item_) {
        if (_items == null) {
            _items = new Inventory();
        }
        _items.add(item_);
    }

    public void add(Mob mob_) {
        _mobs.add(mob_);
        mob_.setRoom(this);
    }

    public void add(Prop p_) {
        _props.add(p_);
    }

    public void addTrack(Track track) {
        if (!isNoTrack) {
            tracks.remove(track);
            tracks.add(track);
        }
    }

    public Exit getExit(String exit_) {
        return _exits.get(exit_);
    }

    public List<Exit> getExits() {
        return _exits;
    }

    public void setExits(String exits_) {
        RoomManager.createExits(this, exits_);
    }

    public Teacher getFirstTeacher() {
        for (Prop prop : _props) {
            if (prop.isTeacher()) {
                return (Teacher) prop;
            }
        }
        return null;
    }

    public Inventory getInventory() {
        if (_items == null) {
            _items = new Inventory();
        }
        return _items;
    }

    public Mob getMob(String input_) {
        return _mobs.get(input_);
    }

    public List<Mob> getMobs(String target) {
        List<Mob> mobs = new ArrayList<Mob>();
        for (Mob mob : _mobs) {
            if (mob.getName().indexOf(target) > -1 || "all".equalsIgnoreCase(target)) {
                mobs.add(mob);
            }
        }
        return mobs;
    }

    public MudArrayList<Mob> getMobs() {
        return _mobs;
    }

    public SomeMoney getMoney() {
        return _items.getPurse();
    }

    @Override
    public String getName() {
        return this.getId();
    }

    public MudArrayList<Prop> getProps() {
        return _props;
    }

    @Override
    public List<String> getSenseFlags() {
        return null;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type_) {
        _type = type_;
    }

    public boolean hasLightSource() {

        // Check room first
        if (_items.hasLightSource()) {
            return true;
        }

        // Check mobs
        for (Mob mob : _mobs) {
            if (mob.getInventory().hasLightSource()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void out(Msg message) {
        for (Mob mob : _mobs) {
            mob.out(message);
        }
    }

    public void out(String message) {
        this.out(new Msg(message));
    }

    public boolean remove(Mob mob_) {
        return _mobs.remove(mob_);

    }

    public boolean remove(Money cash) {
        if (_items.getPurse() != null) {
            return _items.getPurse().remove(cash);
        }
        return false;
    }

    /**
     * Why would you ever what to do this?
     *
     * @param id_
     * @return
     * @deprecated
     */
    @Deprecated
    public Mob remove(String id_) {
        return _mobs.remove(id_);
    }

    public void setDescription(String desc_) {
        // TODO set prop description
    }

    public void setDoors(String doors_) {
        RoomManager.createDoors(this.getId(), doors_);
    }

    public void setExitHidden(String id) {
        Exit exit = _exits.get(id);
        if (exit != null) {
            exit.setHidden(true);
        }
    }

    public void setInitdoors(String notused_) {
        RoomManager.setDoorOnEndOfExit();
    }

    public void setItem(String id) {
        Item item = EntityProvider.createItem(id);
        this.add(item);
    }

    public void setKey(String keys_) {
        RoomManager.setDoorKeys(keys_);
    }

    public void setKeywords(String words_) {
        // TODO create a prop
    }

    public void setMob(String mobId_) {
        Mob mob = EntityProvider.createMob(mobId_, getId());
        this.add(mob);
    }

    public void setProp(String propID_) {
        Prop prop = EntityProvider.createProp(propID_);

        this.add(prop);
    }

    public Mob getRandomPlayer() {
        List<Mob> playerList = new ArrayList<Mob>();
        for (Mob mob : _mobs) {
            if (mob.isPlayer()) {
                playerList.add(mob);
            }
        }
        if (!playerList.isEmpty()) {
            return playerList.get((int) (playerList.size() * Math.random()));
        }
        return null;
    }

    public void addAll(Inventory inventory) {
        _items.addAll(inventory);
    }

    public List<Mob> getFollowers(Mob mob_) {
        List<Mob> group = new ArrayList<>();
        for (Mob mob : _mobs) {
            // Alignment must be the same to form a group
            if (mob.isFollowing(mob_) && (mob.isAlignmentSame(mob_))) {
                group.add(mob);
            }
        }
        return group;
    }

    public ShopKeeper getShopKeeper() {
        for (Mob mob : _mobs) {
            if (mob instanceof ShopKeeper) {
                return (ShopKeeper) mob;
            }
        }
        return null;
    }

    public Mob getRepairer() {
        for (Mob mob : _mobs) {
            if (mob instanceof Armourer) {
                return (Armourer) mob;
            }
        }
        return null;
    }

    public Mob getBanker() {
        for (Mob mob : _mobs) {
            if (mob instanceof Banker) {
                return (Banker) mob;
            }
        }
        return null;
    }

    public void remove(Prop corpse) {
        _props.remove(corpse);
    }

    public boolean isRegen() {
        return isRegen;
    }

    public void setRegen(String arg) {
        isRegen = true;
    }

    public boolean isUnderWater() {
        return isUnderWater;
    }

    public void setUnderWater(boolean underWater) {
        isUnderWater = underWater;
    }
}
