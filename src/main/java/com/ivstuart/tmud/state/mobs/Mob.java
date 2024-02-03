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

package com.ivstuart.tmud.state.mobs;

import com.ivstuart.tmud.common.*;
import com.ivstuart.tmud.constants.CarriedEnum;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.carried.Equipment;
import com.ivstuart.tmud.person.carried.Inventory;
import com.ivstuart.tmud.person.statistics.MobAffects;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.person.statistics.diseases.DiseaseFactory;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.items.Prop;
import com.ivstuart.tmud.state.items.Weapon;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.places.RoomLocation;
import com.ivstuart.tmud.state.player.Attribute;
import com.ivstuart.tmud.state.player.Race;
import com.ivstuart.tmud.state.util.EntityProvider;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.ivstuart.tmud.constants.SpellNames.*;

public class Mob extends Prop implements Tickable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LogManager.getLogger();

    private final EnumSet<MobEnum> enumSet;
    private final MobCombat mobCombat;
    private final MobNpc mobNpc;
    private final MobBodyStats mobBodyStats;
    private final MobCommon mobCommon;

    private final MobCoreStats mobCoreStats;
    private transient Player player;
    private transient Mob following;
    // Player only data?
    private transient Mob lastToldBy;
    private transient Mob possessed;
    private transient MobTimePassing mobTimePassing;
    private transient MobStatus mobStatus;
    private Inventory inventory;
    // Stats
    private Equipment equipment;
    private Learned learned;
    private RoomLocation beforeBattlegroundLocation;
    private RoomLocation roomLocation;

    private MobAffects mobAffects;

    private MobState state;
    private List<Tickable> tickers;
    private transient Mob charmed;
    private transient Mob mount;


    public Mob() {
        enumSet = EnumSet.noneOf(MobEnum.class);
        mobCoreStats = new MobCoreStats();
        mobCombat = new MobCombat(this);
        mobCommon = new MobCommon();
        mobNpc = new MobNpc();
        mobBodyStats = new MobBodyStats();
        mobTimePassing = new MobTimePassing(this);

    }

    public Mob(Mob baseMob) {
        super(baseMob);
        mobCommon = new MobCommon(baseMob.mobCommon);
        state = baseMob.state;

        // Required for diseases.
        if (baseMob.mobAffects != null) {
            mobAffects = new MobAffects(baseMob.mobAffects);
        }

        // equipment not copied.

        if (!baseMob.getInventory().isEmpty()) {
            for (Item item : baseMob.getInventory().getItems()) {
                Item clonedItem = (Item) item.clone();

                if (clonedItem == null) {
                    LOGGER.warn("Trying to add null Item into inventory!");
                }
                else {
                    this.getInventory().add(clonedItem);
                }
            }
        }

        this.enumSet = EnumSet.copyOf(baseMob.enumSet);

        mobCoreStats = new MobCoreStats(baseMob.mobCoreStats);

        mobCombat = new MobCombat(this, baseMob.mobCombat);

        mobNpc = new MobNpc(this, baseMob.mobNpc);

        mobBodyStats = new MobBodyStats(baseMob.mobBodyStats);

        roomLocation = baseMob.roomLocation;
        beforeBattlegroundLocation = baseMob.beforeBattlegroundLocation;
        mobTimePassing = new MobTimePassing(this);

    }

    public MobBodyStats getMobBodyStats() {
        return mobBodyStats;
    }

    public MobCombat getMobCombat() {
        return mobCombat;
    }

    public MobNpc getMobNpc() {
        return mobNpc;
    }

    public boolean hasMobEnum(MobEnum mobEnum) {
        return enumSet.contains(mobEnum);
    }

    public void setMobEnum(MobEnum mobEnum) {
        enumSet.add(mobEnum);
    }

    public void setFlag(String value) {
        try {
            setMobEnum(MobEnum.valueOf(value.toUpperCase()));
        } catch (IllegalArgumentException iae) {
            LOGGER.warn("IllegalArgumentException for flag value:" + value);
        }
    }


    public Mob getPossessed() {
        return possessed;
    }

    public void setPossessed(Mob possessed) {
        this.possessed = possessed;
    }

    public Mob getMount() {
        return mount;
    }

    public void setMount(Mob mount) {
        this.mount = mount;
    }

    public RoomLocation getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(RoomLocation roomLocation) {
        this.roomLocation = roomLocation;
    }

    public void addAffect(String spellId, Affect affect_) {
        if (null == mobAffects) {
            mobAffects = new MobAffects();
        }
        mobAffects.add(spellId, affect_);
    }

    public MobAffects getMobAffects() {
        if (null == mobAffects) {
            mobAffects = new MobAffects();
        }
        return mobAffects;
    }

    public void addTickable(Tickable ticker) {
        if (tickers == null) {
            tickers = new ArrayList<>();
            LOGGER.debug("Setting behaviours [ " + ticker + " ]");
        }
        tickers.add(ticker);

    }

    public int getDefence() {
        return mobCombat.getDefensive();
    }

    public Equipment getEquipment() {
        if (equipment == null) {
            equipment = new Equipment(this);
        }
        return equipment;
    }

    public Fight getFight() {
        // Deserialized mobs will have a null Fight object
        if (mobCombat.getFight() == null) {
            mobCombat.setFight(new Fight(this));
        }

        return mobCombat.getFight();
    }

    public void setFight(Fight fight) {
        mobCombat.setFight(fight);
    }

    public Mob getFollowing() {
        return following;
    }

    public void setFollowing(Mob mob_) {
        following = mob_;
    }

    public Attribute getHp() {
        return mobCoreStats.getHealth();
    }

    public void setHp(String hp) {
        mobNpc.setHp(hp, mobCoreStats.getHealth());
    }

    public Inventory getInventory() {
        if (inventory == null) {
            inventory = new Inventory();
        }
        return inventory;
    }

    public Mob getLastToldBy() {
        return lastToldBy;
    }

    public void setLastToldBy(Mob toldBy_) {
        lastToldBy = toldBy_;
    }

    public Learned getLearned() {
        if (learned == null) {
            learned = new Learned();
        }
        return learned;
    }

    public MobMana getMana() {
        return mobCoreStats.getMana();
    }

    public void setMana(int mana) {
        mobCoreStats.getMana().setToMaximum(mana);
    }

    public int getMobLevel() {
        return mobCommon.getLevel();
    }

    public MobStatus getMobStatus() {
        if (mobStatus == null) {
            mobStatus = new MobStatus();
        }
        return mobStatus;
    }

    public Attribute getMv() {
        return mobCoreStats.getMoves();
    }

    public void setMv(String moves_) {
        mobCoreStats.getMoves().setToMaximum(Integer.parseInt(moves_));
    }

    @Override
    public String getName() {
        return mobCommon.getName();
    }

    public void setName(String name) {
        mobCommon.setName(name);
    }

    public int getOffensive() {
        return mobCombat.getOffensive();
    }

    public Player getPlayer() {
        return player;
    }

    public Room getRoom() {
        // LOGGER.debug("Getting room at location :"+roomLocation);
        return World.getRoom(roomLocation);
    }

    @Override
    public List<String> getSenseFlags() {
        return Collections.emptyList();
    }

    public MobState getState() {
        if (state == null) {
            return MobState.STAND;
        }
        return state;
    }

    public void setState(MobState state_) {
        LOGGER.debug("You set state to " + state_.name());
        state = state_;
    }

    public void setState(String state_) {
        state = MobState.getMobState(state_);
    }

    public Fight getTargetFight() {
        return this.getFight().getTarget().getFight();
    }

    public Weapon getWeapon() {
        if (equipment == null) {
            return null;
        }

        Equipable eq = equipment.getPrimary();

        if (eq instanceof Weapon) {
            return (Weapon) eq;
        }

        return null;
    }

    public Weapon getSecondaryWeapon() {
        if (equipment == null) {
            return null;
        }

        Equipable eq = equipment.getSecondary();

        if (eq instanceof Weapon) {
            return (Weapon) eq;
        }

        return null;
    }

    public int getXp() {
        return mobNpc.getXp();
    }

    public void setXp(int xp) {
        mobNpc.setXp(xp);
    }

    @Override
    public boolean hasDetectHidden() {
        return this.getMobAffects().hasAffect(DETECT_HIDDEN) || getRace().isDetectHidden();
    }

    public boolean isDead() {
        return getHp().getValue() <= 0;
    }

    public boolean isGuarding(Mob mob_, String id) {
        return false;
    }

    public boolean isPlayer() {
        return (player != null);
    }

    public void setPlayer(Player player_) {
        player = player_;
    }

    public boolean isRunning() {
        return mobCommon.isRunning();
    }

    public void setRunning(boolean runFlag) {
        mobCommon.setRunning(runFlag);
    }

    @Override
    public String look() {

        return super.look() + "\n Hp=" + mobCoreStats.getHealth().getValue();
    }

    @Override
    public void out(Msg msg_) {
        LOGGER.debug(this.getName() + " output [ " + msg_.toString(this) + " ]");
        if (player != null) {
            player.out(msg_.toString(this));
        }
        if (possessed != null) {
            possessed.out(msg_);
        }

    }

    public void out(String message) {
        LOGGER.debug(this.getName() + " output [ " + message + " ]");
        if (player != null) {
            player.out(message);
        }
        if (possessed != null) {
            possessed.out(message);
        }
    }

    public void removeAffect(String name_) {
        mobAffects.remove(name_);
    }

    public void setAttackType(String types) {
        mobNpc.setAttackType(types);
    }

    public void setLevel(int level) {
        mobCommon.setLevel(level);
    }

    // Alias will be used in MudArrayList
    public void setNameAndId(String name) {
        mobCommon.setName(name);
        this.setId(name);
    }

    public String showMobAffects() {
        if (mobAffects != null) {
            return mobAffects.look();
        }
        return "";
    }

    @Override
    public boolean tick() {

        if (isDead() && !isPlayer()) {
            return true;
        }

        if (mobTimePassing == null) {
            mobTimePassing = new MobTimePassing(this);
        }
        mobTimePassing.tick();

        return false;
    }

    public boolean isSneaking() {
        return this.getMobStatus().isSneaking();
    }

    public boolean isAdmin() {
        if (this.isPlayer()) {
            return this.player.isAdmin();
        }
        return false;
    }

    public boolean isFlying() {
        return this.getState().isFlying();
    }

    public void addAffect(Affect affect) {
        getMobAffects().add(affect.getDesc(), affect);
    }

    public boolean isGood() {
        if (this.isPlayer()) {
            return (player.getData().getAlignment().getValue() >= 0);
        }
        return mobNpc.isAlignment();
    }

    public boolean isFollowing(Mob mob_) {
        return following == mob_;
    }

    public boolean isAlignmentSame(Mob mob_) {
        return this.isGood() == mob_.isGood();
    }

    public List<Tickable> getTickers() {
        return tickers;
    }

    public void setTickers(List<Tickable> objects) {
        tickers = objects;
    }

    public void setItem(String input) {
        String[] elements = input.split(" ");

        Item item = EntityProvider.createItem(elements[0]);

        if (elements.length == 2) {
            item.setLoadPercentage(Integer.parseInt(elements[1]));
        }
        // LOGGER.debug(("Adding item "+item.getId()+" to mob "+this.getId()));
        this.getInventory().add(item);
    }

    public int getCopper() {
        return mobNpc.getCopper();
    }

    public void setCopper(int copper) {
        mobNpc.setCopper(copper);
    }

    public void setPatrolPath(String path) {
        mobNpc.setPatrolPath(path);
    }

    public void setArmour(int armour) {
        mobNpc.setArmour(armour);
    }

    public int getWeightCarried() {
        int grams = 0;
        if (inventory != null) {
            grams += inventory.getWeight();
        }
        if (equipment != null) {
            grams += equipment.getWeight();
        }
        return grams;
    }

    public CarriedEnum getBurden() {
        int maxCarry = 80 * this.getPlayer().getAttributes().getSTR().getValue() / 15;

        int kgs = this.getWeightCarried() / 1000;

        int index = kgs * CarriedEnum.values().length / maxCarry;

        if (index >= CarriedEnum.values().length) {
            index = CarriedEnum.values().length - 1;
        }

        return CarriedEnum.get(index);
    }

    public void setReturnRoom() {
        beforeBattlegroundLocation = roomLocation;
    }

    public RoomLocation getReturnRoom() {
        return beforeBattlegroundLocation;
    }

    @Override
    public boolean hasSeeInDark() {
        return getRace().isInfravison() || this.getMobAffects().hasAffect(INFRAVISION);
    }

    @Override
    public boolean isBlinded() {
        return this.getMobAffects().hasAffect(BLINDNESS);
    }

    @Override
    public boolean isInDark() {
        if (getRoom() == null) {
            return false;
        }

        return getRoom().isInDark();
    }

    @Override
    public boolean isSleeping() {
        if (state == null) {
            return false;
        }
        return state.isSleeping();
    }

    @Override
    public boolean isInvisible() {
        return super.isInvisible() && this.getMobAffects().hasAffect(INVISIBILITY);
    }

    public boolean hasBoat() {
        return getInventory().hasBoat();
    }

    public int getSave(DamageType damageType) {
        if (isPlayer()) {
            return getEquipment().getSave(damageType);
        }
        if (mobNpc.getSaves() == null) {
            return 0;
        }
        return mobNpc.getSaves().get(damageType);
    }

    public int getWimpy() {
        return mobCombat.getWimpy();
    }

    public void setDisease(String name) {
        Disease disease = DiseaseFactory.createClass(name);

        if (disease == null) {
            LOGGER.warn("Problem creating disease for " + this.getName());
            return;
        }

        disease.setMob(this);
        disease.setDecription(name);
        getMobAffects().add(disease.getId(), disease);
        LOGGER.info("Adding disease to " + this.getName() + " " + disease);
    }

    @Override
    public String toString() {
        return "Mob{" +
                "enumSet=" + enumSet +
                ", mobCombat=" + mobCombat +
                ", mobNpc=" + mobNpc +
                ", mobBodyStats=" + mobBodyStats +
                ", mobCommon=" + mobCommon +
                ", inventory=" + inventory +
                ", equipment=" + equipment +
                ", player=" + player +
                ", following=" + following +
                ", lastToldBy=" + lastToldBy +
                ", possessed=" + possessed +
                ", mobTimePassing=" + mobTimePassing +
                ", learned=" + learned +
                ", beforeBattlegroundLocation=" + beforeBattlegroundLocation +
                ", roomLocation=" + roomLocation +
                ", mobCoreStats=" + mobCoreStats +
                ", mobAffects=" + mobAffects +
                ", mobStatus=" + mobStatus +
                ", state=" + state +
                ", tickers=" + tickers +
                ", charmed=" + charmed +
                ", mount=" + mount +
                '}';
    }

    public Mob getCharmed() {
        return charmed;
    }

    public void setCharmed(Mob charmed) {
        this.charmed = charmed;
    }

    public boolean isRiding() {
        return mount != null;
    }

    public void clearAffects() {

        if (isPlayer()) {
            getPlayer().getData().restoreToPerfectHealth();
        }

        // Best to remove effects first
        getMobAffects().removeAll();

        getMobAffects().clear();

    }

    public void setBehaviour(String behaviour) {
        mobNpc.setBehaviour(behaviour);
    }

    public Race getRace() {
        // TODO test this carefully and check if it is better to cache Race object in mob.
        return World.getInstance().getRace(mobBodyStats.getRaceId());
    }
    @Override
    public Gender getGender() {
        return mobBodyStats.getGender();
    }

    public void setRoomLocationToBeforeBattleGround() {
        roomLocation = beforeBattlegroundLocation;
    }

    @Deprecated
    public void setRoom(Room room) {
        roomLocation = room.getRoomLocation();
    }

    public MobCoreStats getMobCoreStats() {
        return mobCoreStats;
    }
}
