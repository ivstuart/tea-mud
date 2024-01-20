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

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Msgable;
import com.ivstuart.tmud.constants.SectorType;
import com.ivstuart.tmud.person.carried.Inventory;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.person.statistics.diseases.DiseaseFactory;
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

    private transient MudArrayList<Prop> _props;

    private transient MudArrayList<Exit> _exits;

    private transient MudArrayList<Mob> _mobs;

    private transient Inventory _items;
    private transient List<Disease> diseases;
    private boolean isRegen;
    private boolean isUnderWater;
    private boolean isDark;
    private boolean isWater;
    private boolean isDeepWater;
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
    private boolean isAdminRoom;
    private boolean isHouse;
    private boolean isClimb;
    private boolean isNoDrop;
    private boolean isAuctionHouse;
    private SectorType sectorType;

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
        this.isAdminRoom = room.isAdminRoom;
        this.isHouse = room.isHouse;
        this.isClimb = room.isClimb;
        this.isNoDrop = room.isNoDrop;
        this.isAuctionHouse = room.isAuctionHouse;
        this.isDeepWater = room.isDeepWater;
        this._type = room._type;
        this.sectorType = room.sectorType;

    }

    public static void setPickable(boolean flag) {
        RoomManager.setDoorPickable(flag);
    }

    public static void setUnspellable(boolean flag) {
        RoomManager.setDoorUnspellable(flag);
    }

    public static void setDifficulty(int percentage) {
        RoomManager.setDoorDifficulty(percentage);
    }

    public static void setStrength(int str) {
        RoomManager.setDoorStrength(str);
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public boolean isDeepWater() {
        return isDeepWater;
    }

    public void setDeepWater(boolean deepWater) {
        isDeepWater = deepWater;
    }

    public boolean isAuctionHouse() {
        return isAuctionHouse;
    }

    public void setAuctionHouse(boolean auctionHouse) {
        isAuctionHouse = auctionHouse;
    }

    public SectorType getSectorType() {
        if (sectorType == null) {
            return SectorType.INSIDE;
        }
        return sectorType;
    }

    public void setSectorType(String sectorType) {
        this.sectorType = SectorType.valueOf(sectorType);
    }

    @Override
    public String toString() {
        return "Room{" +
                "tracks=" + tracks +
                ", _type='" + _type + '\'' +
                ", _props=" + _props +
                ", _exits=" + _exits +
                // ", _mobs=" + _mobs +
                ", _items=" + _items +
                ", diseases=" + diseases +
                ", isRegen=" + isRegen +
                ", isUnderWater=" + isUnderWater +
                ", isDark=" + isDark +
                ", isWater=" + isWater +
                ", isDeepWater=" + isDeepWater +
                ", isFlying=" + isFlying +
                ", isDeath=" + isDeath +
                ", isNoMob=" + isNoMob +
                ", isIndoors=" + isIndoors +
                ", isPeaceful=" + isPeaceful +
                ", isSoundProof=" + isSoundProof +
                ", isNoTrack=" + isNoTrack +
                ", isNoMagic=" + isNoMagic +
                ", isTunnel=" + isTunnel +
                ", isPrivate=" + isPrivate +
                ", isAdminRoom=" + isAdminRoom +
                ", isHouse=" + isHouse +
                ", isClimb=" + isClimb +
                ", isNoDrop=" + isNoDrop +
                ", isAuctionHouse=" + isAuctionHouse +
                ", sectorType=" + sectorType +
                '}';
    }

    public boolean isNoDrop() {
        return isNoDrop;
    }

    public void setNoDrop(boolean noDrop) {
        isNoDrop = noDrop;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
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

    public boolean isAdminRoom() {
        return isAdminRoom;
    }

    public void setAdminRoom(boolean adminRoom) {
        isAdminRoom = adminRoom;
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
        _props = new MudArrayList<>();
        _exits = new MudArrayList<>();
        _mobs = new MudArrayList<>(true); // matching part of name
        tracks = new ArrayList<>(0);
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
        // For locate spell
        item_.setRoomId(this.getId());
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
        List<Mob> mobs = new ArrayList<>();
        for (Mob mob : _mobs) {
            if (mob.getName().contains(target) || "all".equalsIgnoreCase(target)) {
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
     * @param id_ key
     * @return Mob
     * @deprecated
     */
    @Deprecated
    public Mob remove(String id_) {
        return _mobs.remove(id_);
    }

    public void setProp(String propId) {
        Prop prop = EntityProvider.createProp(propId);
        if (prop == null) {
            LOGGER.error("No prop found with id:" + propId);
            return;
        }
        LOGGER.debug("Adding prop to room " + prop);
        this.add(prop);
    }

    public void setDoors(String doors_) {
        RoomManager.createDoors(this.getId(), doors_);
    }

    public void setBashable(boolean isBashable) {
        RoomManager.setDoorBashable(isBashable);
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

    public void setMob(String mobId_) {
        Mob mob = EntityProvider.createMob(mobId_, getId());
        this.add(mob);
    }

    public Mob getRandomPlayer() {
        List<Mob> playerList = new ArrayList<>();
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
                return mob;
            }
        }
        return null;
    }

    public Mob getBanker() {
        for (Mob mob : _mobs) {
            if (mob instanceof Banker) {
                return mob;
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

    public void setRegen(boolean regen) {
        isRegen = regen;
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

    public Mob getWarMaster() {
        for (Mob mob : _mobs) {
            if (mob instanceof WarMaster) {
                return mob;
            }
        }
        return null;
    }

    public Room getGroundRoom() {
        Room room = this;
        Exit exit = this.getExit("down");
        while (room.isFlying() && exit != null) {
            room = exit.getDestinationRoom();
            exit = this.getExit("down");
        }

        return room;
    }

    public ProfessionMaster getProfessionMaster() {
        for (Mob mob : _mobs) {
            if (mob instanceof ProfessionMaster) {
                return (ProfessionMaster) mob;
            }
        }
        return null;
    }

    public boolean hasFire() {
        Prop prop = _props.get("fire");
        return prop != null;
    }

    public void add(Disease disease) {
        if (diseases == null) {
            diseases = new ArrayList<>();
        }

        for (Disease haveAlreadyDiseases : diseases) {
            if (disease.getDesc().equals(haveAlreadyDiseases.getDesc())) {
                // No need to add something the room already has
                LOGGER.info("Room already has that disease not adding");
                return;
            }
        }

        diseases.add(disease);
    }

    public void setDisease(String name) {
        Disease disease = DiseaseFactory.createClass(name);

        if (disease == null) {
            // TODO log this case.
            return;
        }

        disease.setDecription(name);
        add(disease);
    }
}
