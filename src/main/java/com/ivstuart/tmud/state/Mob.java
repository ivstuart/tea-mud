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

import com.ivstuart.tmud.behaviour.BaseBehaviour;
import com.ivstuart.tmud.behaviour.BehaviourFactory;
import com.ivstuart.tmud.command.misc.ForcedQuit;
import com.ivstuart.tmud.common.*;
import com.ivstuart.tmud.constants.CarriedEnum;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.Learned;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.carried.Equipment;
import com.ivstuart.tmud.person.carried.Inventory;
import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.person.statistics.MobAffects;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.person.statistics.diseases.DiseaseFactory;
import com.ivstuart.tmud.server.ConnectionManager;
import com.ivstuart.tmud.state.util.EntityProvider;
import com.ivstuart.tmud.world.MoonPhases;
import com.ivstuart.tmud.world.WeatherSky;
import com.ivstuart.tmud.world.World;
import com.ivstuart.tmud.world.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.ivstuart.tmud.constants.SpellNames.*;

public class Mob extends Prop implements Tickable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LogManager.getLogger();

    protected Inventory inventory;
    protected Equipment equipment;

    protected transient Player player;
    protected transient Fight fight;
    protected transient Mob following;

    private MobCoreStats mobCoreStats;

    // Stats
    protected Gender gender;
    protected String race;


    protected int height; // cms base mob

    // Player only data?
    protected transient Mob lastToldBy;
    protected Learned learned;

    protected String repopRoomID;
    protected transient Room room;
    protected String roomId;
    protected int weight; // kg base mob
    private String ability; // base mob
    private int align; // base mob
    private int armour;
    private String attackType; // base mob
    private int copper;
    private DiceRoll damage; // base mob
    private int attacks = 1;
    private int defensive;
    private int offensive;
    private transient boolean running = false;
    private int level;
    private DiceRoll maxHp;
    private MobAffects mobAffects;
    private transient MobStatus mobStatus;
    private String name;
    private MobState state;
    private int xp;
    private int raceId;
    private int wimpy;
    private boolean undead;
    private transient List<String> behaviours;
    private List<Tickable> tickers;
    private final int RATE_OF_REGEN_3_PERCENT = 3;
    private String patrolPath;
    private boolean alignment;
    private String returnRoom; // After battleground will be returned here.
    private int fallingCounter;
    private Map<DamageType, Integer> saves;
    private Mob charmed;
    private Mob mount;
    private transient Mob possessed;

    private final EnumSet<MobEnum> enumSet;

    public Mob() {
        fight = new Fight(this);
        enumSet = EnumSet.noneOf(MobEnum.class);
        mobCoreStats = new MobCoreStats();
    }

    public Mob(Mob baseMob) {
        super(baseMob);
        fight = new Fight(this);

        name = baseMob.name;
        armour = baseMob.armour;

        state = baseMob.state;

        level = baseMob.level;
        align = baseMob.align;
        attacks = baseMob.attacks;
        damage = baseMob.damage;
        offensive = baseMob.offensive;
        defensive = baseMob.defensive;
        copper = baseMob.copper;
        xp = baseMob.xp;

        attackType = baseMob.attackType;
        ability = baseMob.ability;

        gender = baseMob.gender;
        race = baseMob.race;
        raceId = baseMob.raceId;

        height = baseMob.height;
        weight = baseMob.weight;

        room = baseMob.room;

        repopRoomID = baseMob.repopRoomID;

        alignment = baseMob.alignment;

        // Required for diseases.
        if (baseMob.mobAffects != null) {
            mobAffects = (MobAffects) baseMob.mobAffects.clone();
        }

        if (baseMob.behaviours != null) {
            for (String behaviour : baseMob.behaviours) {
                BaseBehaviour bb = BehaviourFactory.create(behaviour);

                if (bb != null) {
                    bb.setMob(this);
                    if (tickers == null) {
                        tickers = new ArrayList<>();
                    }
                    LOGGER.debug("Adding behaviour [" + bb.getId() + "] for mob " + this.getName());
                    tickers.add(bb);

                }


            }
        }

        if (!baseMob.getInventory().isEmpty()) {
            this.getInventory().addAll(baseMob.getInventory().getItems());
        }

        if (tickers != null || mobAffects != null) {
            LOGGER.debug("Adding to world time for mob " + this.getName());
            WorldTime.addTickable(this);
        }

        this.enumSet = EnumSet.copyOf(baseMob.enumSet);

        mobCoreStats = new MobCoreStats(baseMob.mobCoreStats);

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
        }
        catch (IllegalArgumentException iae) {
            LOGGER.warn("IllegalArgumentException for flag value:"+value);
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

    public void setSaves(String saveString) {
        String[] element = saveString.split(":");
        if (element.length != 2) {
            LOGGER.error("Problem setting saves to " + saveString);
            return;
        }
        if (saves == null) {
            saves = new HashMap<>();
        }
        saves.put(DamageType.valueOf(element[0]), Integer.parseInt(element[1]));
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isAlignment() {
        return alignment;
    }

    public void setAlignment(boolean alignment) {
        this.alignment = alignment;
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

    public DiceRoll getDamage() {
        return damage;
    }

    public void setDamage(DiceRoll damage) {
        this.damage = damage;
    }

    public void setDamage(String damage_) {
        this.damage = new DiceRoll(damage_);
    }

    public int getDefence() {
        return defensive;
    }

    public Equipment getEquipment() {
        if (equipment == null) {
            equipment = new Equipment(this);
        }
        return equipment;
    }

    public Fight getFight() {
        if (fight == null) {
            fight = new Fight(this);
        }
        return fight;
    }

    public void setFight(Fight fight) {
        this.fight = fight;
    }

    public Mob getFollowing() {
        return following;
    }

    public void setFollowing(Mob mob_) {
        following = mob_;
    }

    @Override
    public Gender getGender() {

        return gender;
    }

    public void setGender(Gender g) {
        gender = g;
    }

    public void setGender(String gender_) {
        gender = Gender.valueOf(gender_.toUpperCase());
    }

    public Attribute getHp() {
        return mobCoreStats.getHealth();
    }

    public void setHp(String hp_) {
        maxHp = new DiceRoll(hp_);
        mobCoreStats.getHealth().setToMaximum(maxHp.roll());
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
        return level;
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
        return name;
    }

    public void setName(String name_) {
        name = name_;
    }

    public int getOffensive() {
        return offensive;
    }

    public void setOffensive(String offensive_) {
        this.offensive = Integer.parseInt(offensive_.trim());
    }

    public Player getPlayer() {
        return player;
    }

    public String getRaceName() {
        return race;
    }

    public String getRepopRoomId() {
        return repopRoomID;
    }

    public void setRepopRoomId(String repopRoomId_) {
        repopRoomID = repopRoomId_;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room_) {
        room = room_;

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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight_) {
        weight = weight_;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(String xp_) {
        // _stats.add(new IntegerAttribute(XP,xp_));
        this.xp = Integer.parseInt(xp_);
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
        return running;
    }

    public void setRunning(boolean runFlag) {
        running = runFlag;
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

    public void setAbility(String ability_) {
        this.ability = ability_;
    }

    public void setAlign(String align_) {
        this.align = Integer.parseInt(align_);
    }

    public int getAttacks() {
        return attacks;
    }

    public void setAttacks(String attacks) {
        this.attacks = Integer.parseInt(attacks);
    }

    public void setAttackType(String types) {
        this.attackType = types;
    }

    public void setBehaviour(String behaviours) {
        if (this.behaviours == null) {
            this.behaviours = new ArrayList<>();
        }
        this.behaviours.add(behaviours);
    }

    public void setDefensive(String defensive) {
        this.defensive = Integer.parseInt(defensive.trim());
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Alias will be used in MudArrayList
    public void setNameAndId(String name) {
        this.name = name;
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

        tickRegenerate();

        checkForDrowning();

        checkForFalling();

        checkHungerAndThirst();

        checkIdleTimeAndKickout();

        checkForDiseases();

        checkForMobAffects();

        checkForMobBehaviour();

        checkForHitByLightning();

        return false;
    }

    private void checkForHitByLightning() {

        if (World.getWeather() == WeatherSky.LIGHTNING) {
            if (room.getSectorType().isInside() && DiceRoll.ONE_D100.rollLessThanOrEqualTo(1) &&
                    DiceRoll.ONE_D100.rollLessThanOrEqualTo(1)) {
                out("You are hit by lightning!");
                mobCoreStats.getHealth().increasePercentage(-1 * DiceRoll.roll(2, 100, 0));
                DamageManager.checkForDefenderDeath(this, this);
            }
        }


    }

    private void checkForMobAffects() {
        if (null != mobAffects) {
            mobAffects.tick();
        }
    }

    private void checkForMobBehaviour() {
        if (tickers != null) {
            for (Tickable ticker : tickers) {
                ticker.tick();
            }
        }
    }

    private void tickRegenerate() {

        MobState currentState = MobState.STAND;

        if (state != null) {
            currentState = state;
        }

        int regen_rate = RATE_OF_REGEN_3_PERCENT;
        if (room.hasFlag(RoomEnum.REGEN)) {
            regen_rate *= 3;
        } else {
            if (!room.getSectorType().isInside() && MoonPhases.isNightTime()) {
                regen_rate = regen_rate * MoonPhases.getPhase().getManaMod();
            }
        }

        mobCoreStats.regen(currentState, regen_rate);
    }

    private void checkForDrowning() {
        if (mobCoreStats.getHealth() != null) {

            if (room.hasFlag(RoomEnum.UNDER_WATER) &&
                    !getRace().isWaterbreath() &&
                    !mobAffects.hasAffect(UNDERWATER_BREATH)) {
                out("You begin to drown as you can not breath underwater");
                mobCoreStats.getHealth().increasePercentage(-20);
                DamageManager.checkForDefenderDeath(this, this);
            }
        }

    }

    private void checkForDiseases() {
        if (!getRace().isUndead()) {
            List<Disease> diseases = this.getMobAffects().getDiseases();
            for (Disease disease : diseases) {
                if (disease.isDroplets()) {
                    // LOGGER.debug(getName()+" infecting mobs in room with "+disease.getDesc());
                    infectMobs(getRoom().getMobs(), disease);
                }
                if (disease.isAirbourne() && DiceRoll.ONE_D100.rollLessThanOrEqualTo(disease.getInfectionRate())) {
                    getRoom().add(disease);
                }
            }
        }
    }

    private void infectMobs(List<Mob> mobs, Disease disease) {

        for (Mob mob : mobs) {
            if (mob == this) {
                continue;
            }
            Disease.infect(mob, disease);
        }

    }

    private void checkIdleTimeAndKickout() {
        if (isPlayer() && !getFight().isEngaged()) {
            getPlayer().getConnection().checkTimeout(this);
        }
    }

    private void checkHungerAndThirst() {
        if (isPlayer() && !getRace().isUndead()) {
            Attribute thirst = getPlayer().getData().getThirst();
            thirst.decrease(1);
            Attribute hunger = getPlayer().getData().getHunger();
            hunger.decrease(1);
            if (thirst.getValue() <= 0) {
                out("You hurt from thirst!");
                mobCoreStats.getHealth().increasePercentage(-15);
            }
            if (hunger.getValue() <= 0) {
                out("You hurt from hunger!");
                mobCoreStats.getHealth().increasePercentage(-10);
            }

            Attribute drunk = getPlayer().getData().getDrunkAttribute();
            Attribute poison = getPlayer().getData().getPoisonAttribute();

            if (poison.getValue() > 0) {
                out("You hurt from poison!");
                mobCoreStats.getHealth().increasePercentage(-10);
                poison.decrease(1);
            }

            if (drunk.getValue() > 0) {
                out("You slowly sober up, coffee might help");
                drunk.decrease(1);
            }
            DamageManager.checkForDefenderDeath(this, this);
        }
    }

    private void checkForFalling() {
        if (this.isFlying() && fallingCounter > 0) {
            fallingCounter = 0;
        }

        // Falling from the sky. 30% damage per room
        if (room.hasFlag(RoomEnum.AIR) && !this.isFlying() && !getState().isMeditate()) {
            this.out("You are in free fall while not flying");
            fallingCounter++;
            MoveManager.move(this, "down");
        }

        if (!room.hasFlag(RoomEnum.AIR) && fallingCounter > 0) {
            this.out("Ouch, you hit the ground hard!");
            if (room.hasFlag(RoomEnum.WATER)) {
                mobCoreStats.getHealth().increasePercentage(-10 * fallingCounter);
            } else {
                mobCoreStats.getHealth().increasePercentage(-30 * fallingCounter);
            }
            fallingCounter = 0;
            DamageManager.checkForDefenderDeath(this, this);
        }
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
        return alignment;
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
        return copper;
    }

    public void setCopper(String copper_) {
        this.copper = Integer.parseInt(copper_);
    }

    public void setPatrolPath(String path) {
        this.patrolPath = path;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(String armour_) {
        this.armour = Integer.parseInt(armour_);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height_) {
        height = height_;
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

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public Race getRace() {
        return World.getInstance().getRace(raceId);
    }

    public void setRace(String race_) {
        race = race_;
    }

    public void setUndead(boolean undead) {
        this.undead = undead;
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
        returnRoom = room.getId();
    }

    public String getReturnRoom() {
        return returnRoom;
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
        if (room == null) {
            return false;
        }

        return room.isInDark();
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
        if (saves == null) {
            return 0;
        }
        return saves.get(damageType);
    }

    public int getWimpy() {
        return wimpy;
    }

    public void setWimpy(int wimpy) {
        this.wimpy = wimpy;
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
                "equipment=" + equipment +
                ", fight=" + fight +
                ", following=" + following +
                ", gender=" + gender +
                ", height=" + height +
                ", inventory=" + inventory +
                ", lastToldBy=" + lastToldBy +
                ", learned=" + learned +
                ", player=" + player +
                ", race='" + race + '\'' +
                ", repopRoomID='" + repopRoomID + '\'' +
                // ", room=" + room +
                ", roomId='" + roomId + '\'' +
                ", weight=" + weight +
                ", ability='" + ability + '\'' +
                ", align=" + align +
                ", armour=" + armour +
                ", attackType='" + attackType + '\'' +
                ", copper=" + copper +
                ", damage=" + damage +
                ", attacks=" + attacks +
                ", defensive=" + defensive +
                ", offensive=" + offensive +
                ", running=" + running +
                ", level=" + level +
                ", maxHp=" + maxHp +
                ", mobAffects=" + mobAffects +
                ", mobStatus=" + mobStatus +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", xp=" + xp +
                ", raceId=" + raceId +
                ", wimpy=" + wimpy +
                ", undead=" + undead +
                ", behaviours=" + behaviours +
                ", tickers=" + tickers +
                ", RATE_OF_REGEN_3_PERCENT=" + RATE_OF_REGEN_3_PERCENT +
                ", patrolPath='" + patrolPath + '\'' +
                ", alignment=" + alignment +
                ", returnRoom='" + returnRoom + '\'' +
                ", fallingCounter=" + fallingCounter +
                ", saves=" + saves +
                ", flags=" + enumSet +
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

            Attribute drunk = getPlayer().getData().getDrunkAttribute();
            Attribute poison = getPlayer().getData().getPoisonAttribute();

            drunk.setValue(0);
            poison.setValue(0);

            getPlayer().getData().getHunger().setValue(500);
            getPlayer().getData().getThirst().setValue(500);
        }

        // Best to remove effects first
        getMobAffects().removeAll();

        getMobAffects().clear();

    }
}
