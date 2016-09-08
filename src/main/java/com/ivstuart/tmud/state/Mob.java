/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.person.statistics.MobAffects;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.person.statistics.diseases.DiseaseFactory;
import com.ivstuart.tmud.state.util.EntityProvider;
import com.ivstuart.tmud.world.MoonPhases;
import com.ivstuart.tmud.world.WeatherSky;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.ivstuart.tmud.constants.SpellNames.*;

public class Mob extends Prop implements Tickable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LogManager.getLogger();
    protected Equipment equipment;
    protected transient Fight fight;
    protected transient Mob following;
    // Stats
    protected Gender gender; // base mob
    protected Attribute health;
    protected int height; // cms base mob
    protected Inventory inventory;
    // Player only data?
    protected transient Mob lastToldBy;
    protected Learned learned;
    protected MobMana mana;
    protected Attribute moves;
    protected transient Player player;
    protected String race; // base mob
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
    private boolean immunityTackle = false;
    private transient boolean running = false;
    private int level; // base mob
    private DiceRoll maxHp; // base mob
    private MobAffects mobAffects;
    private transient MobStatus mobStatus;
    private String name; // immutable
    private MobState state;
    private int xp;
    private int raceId;
    private int wimpy;
    private boolean undead;
    private transient List<String> behaviours;
    private List<Tickable> tickers;
    private int RATE_OF_REGEN_3_PERCENT = 3;
    private String patrolPath;
    private boolean alignment;
    private boolean isAware;
    private boolean isNoSummon;
    private boolean isNoSleep;
    private boolean isNoBash;
    private boolean isNoBlind;
    private boolean isNoCharm;
    private String returnRoom; // After battleground will be returned here.
    private int fallingCounter;
    private boolean veryAggressive;
    private boolean isMemory;
    private boolean isPeekAggro;
    private int IDLE_TIMEOUT = 500; // Seconds
    private Map<DamageType, Integer> saves;
    private Mob charmed;
    public Mob() {
        fight = new Fight(this);
    }
    public Mob(Mob baseMob) {
        super(baseMob);
        fight = new Fight(this);

        name = baseMob.name;
        armour = baseMob.armour;
        health = new Attribute("Health", baseMob.maxHp.roll());
        if (null != baseMob.moves) {
            moves = new Attribute("Move", baseMob.moves.maximum);
        }
        if (null != baseMob.mana) {
            mana = new MobMana(baseMob.mana);
        }
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

        isAware = baseMob.isAware;
        isNoSummon = baseMob.isNoSummon;
        isNoSleep = baseMob.isNoSleep;
        isNoBash = baseMob.isNoBash;
        isNoBlind = baseMob.isNoBlind;
        veryAggressive = baseMob.veryAggressive;
        isMemory = baseMob.isMemory;
        isNoCharm = baseMob.isNoCharm;

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

    }

    public boolean isNoCharm() {
        return isNoCharm;
    }

    public void setNoCharm(boolean noCharm) {
        isNoCharm = noCharm;
    }

    public void setSaves(String saveString) {
        String element[] = saveString.split(":");
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

    public boolean isPeekAggro() {
        return isPeekAggro;
    }

    public void setPeekAggro(boolean peekAggro) {
        isPeekAggro = peekAggro;
    }

    public boolean isAlignment() {
        return alignment;
    }

    public void setAlignment(boolean alignment) {
        this.alignment = alignment;
    }

    public boolean isAware() {
        return isAware;
    }

    public void setAware(boolean aware) {
        isAware = aware;
    }

    public boolean isNoSummon() {
        return isNoSummon;
    }

    public void setNoSummon(boolean noSummon) {
        isNoSummon = noSummon;
    }

    public boolean isNoSleep() {
        return isNoSleep;
    }

    public void setNoSleep(boolean noSleep) {
        isNoSleep = noSleep;
    }

    public boolean isNoBash() {
        return isNoBash;
    }

    public void setNoBash(boolean noBash) {
        isNoBash = noBash;
    }

    public boolean isNoBlind() {
        return isNoBlind;
    }

    public void setNoBlind(boolean noBlind) {
        isNoBlind = noBlind;
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
            tickers = new ArrayList<Tickable>();
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
        return health;
    }

    public void setHp(String hp_) {
        maxHp = new DiceRoll(hp_);
        health = new Attribute("Health", maxHp.roll());
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
        return mana;
    }

    public void setMana(MobMana mana_) {
        mana = mana_;
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
        return moves;
    }

    public void setMv(String moves_) {
        moves = new Attribute("Move", Integer.parseInt(moves_));
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

    public boolean isAlive() {
        return !isDead();
    }

    public boolean isDead() {
        return getHp().getValue() < 0;
    }

    public boolean isGuard() {
        return false;
    }

    public boolean isGuarding(Mob mob_, String id) {
        return false;
    }

    public boolean isImmunityTackle() {
        return immunityTackle;
    }

    public void setImmunityTackle(boolean immunityTackle) {
        this.immunityTackle = immunityTackle;
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

        return super.look() + "\n Hp=" + health.getValue();
    }

    @Override
    public void out(Msg msg_) {
        LOGGER.debug(this.getName() + " output [ " + msg_.toString(this) + " ]");
        if (player != null) {
            player.out(msg_.toString(this));
        }

    }

    public void out(String message) {
        LOGGER.debug(this.getName() + " output [ " + message + " ]");

        if (player != null) {

            player.out(message);
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

    public void setBehaviour(String behaviours_) {
        if (this.behaviours == null) {
            this.behaviours = new ArrayList<String>();
        }
        this.behaviours.add(behaviours_);
    }

    public void setDefensive(String defensive_) {
        this.defensive = Integer.parseInt(defensive_.trim());
    }

    public void setLevel(int level_) {
        this.level = level_;
    }

    // Alias will be used in MudArrayList
    public void setNameAndId(String name_) {
        name = name_;
        this.setId(name_);
    }

    public String showMobAffects() {
        if (mobAffects != null) {
            return mobAffects.look();
        }
        return "";
    }

    @Override
    public boolean tick() {

        tickRegenerate();

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
            if (!room.getSectorType().isInside() && DiceRoll.ONE_D100.rollLessThanOrEqualTo(1) &&
                    DiceRoll.ONE_D100.rollLessThanOrEqualTo(1)) {
                out("You are hit by lightning!");
                health.increasePercentage(-1 * DiceRoll.roll(2, 100, 0));
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

        if (room.isRegen()) {
            RATE_OF_REGEN_3_PERCENT = 9;
        } else {
            if (!room.getSectorType().isInside() && MoonPhases.isNightTime()) {
                RATE_OF_REGEN_3_PERCENT = 3 * MoonPhases.getPhase().getManaMod();
            }
            RATE_OF_REGEN_3_PERCENT = 3;
        }

        if (health != null) {

            if (room.isUnderWater() &&
                    !getRace().isWaterbreath() &&
                    !mobAffects.hasAffect(UNDERWATER_BREATH)) {
                out("You begin to drown as you can not breath underwater");
                health.increasePercentage(-20);
                DamageManager.checkForDefenderDeath(this, this);
            } else {
                health.increasePercentage(RATE_OF_REGEN_3_PERCENT * currentState.getHpMod());
                health.increase(this.getRace().getRegenHp());
            }
        }
        if (moves != null) {
            moves.increasePercentage(RATE_OF_REGEN_3_PERCENT * currentState.getMoveMod());
            moves.increase(this.getRace().getRegenMv());
        }
        if (mana != null) {
            mana.increasePercentage(RATE_OF_REGEN_3_PERCENT * currentState.getManaMod());
            mana.increase(this.getRace().getRegenMn());
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
            int secondsIdle = (int) (getPlayer().getConnection().getIdle() / 1000);

            if (secondsIdle > IDLE_TIMEOUT) {
                LOGGER.info("Player " + getPlayer().getName() + " has been idle for " + secondsIdle + " and has been kicked off");
                out("You have been idle for " + secondsIdle + " seconds hence quiting for you");
                new ForcedQuit().execute(this, null);
            } else if (!getPlayer().getConnection().isConnected()) {
                LOGGER.info("Player has lost there connection and has been kicked off");
                new ForcedQuit().execute(this, null);
            }

            // LOGGER.debug("Tick for player " + getPlayer().getName());

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
                health.increasePercentage(-15);
            }
            if (hunger.getValue() <= 0) {
                out("You hurt from hunger!");
                health.increasePercentage(-10);
            }

            Attribute drunk = getPlayer().getData().getDrunkAttribute();
            Attribute poison = getPlayer().getData().getPoisonAttribute();

            if (poison.getValue() > 0) {
                out("You hurt from poison!");
                health.increasePercentage(-10);
                poison.decrease(1);
            }

            if (drunk.getValue() > 0) {
                out("You slowly sober up, coffee might help");
                drunk.decrease(1);
            }
        }
    }

    private void checkForFalling() {
        if (this.isFlying() && fallingCounter > 0) {
            fallingCounter = 0;
        }

        // Falling from the sky. 30% damage per room
        if (room.isFlying() && !this.isFlying() && !getState().isMeditate()) {
            this.out("You are in free fall while not flying");
            fallingCounter++;
            MoveManager.move(this, "down");
        }

        if (!room.isFlying() && fallingCounter > 0) {
            this.out("Ouch, you hit the ground hard!");
            if (room.isWater()) {
                health.increasePercentage(-10 * fallingCounter);
            } else {
                health.increasePercentage(-30 * fallingCounter);
            }
            fallingCounter = 0;
            DamageManager.checkForDefenderDeath(this, this);
        }
    }

    public boolean isSneaking() {
        return mobStatus.isSneaking();
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
        mobAffects.add(affect.getDesc(), affect);
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

    /**
     * Based on level
     *
     * @return
     */
    public String getSize() {
        if (level < 10) {
            return "small";
        } else if (level < 20) {
            return "medium";
        } else if (level < 40) {
            return "big";
        } else if (level < 60) {
            return "large";
        } else if (level < 80) {
            return "huge";
        } else {
            return "massive";
        }
    }

    /**
     * @return
     */
    public String getAge() {
        if (isPlayer()) {
            int remorts = getPlayer().getData().getRemorts();
            switch (remorts) {
                case 0:
                    return "young";
                case 1:
                    return "youthful";
                case 2:
                    return "middle aged";
                case 3:
                    return "mature";
                case 4:
                    return "old";
                case 5:
                    return "very old";
                case 6:
                    return "ancient";
                default:
                    return "unknown";
            }
        }
        return "";
    }

    public List<Tickable> getTickers() {
        return tickers;
    }

    public void setItem(String input) {
        String elements[] = input.split(" ");

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
        int grams = inventory.getWeight();
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

    public boolean isVeryAggressive() {
        return veryAggressive;
    }

    public void setVeryAggressive(boolean veryAggressive) {
        this.veryAggressive = veryAggressive;
    }

    public boolean isMemory() {
        return isMemory;
    }

    public void setMemory(boolean memory) {
        isMemory = memory;
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
        if (saves == null) {
            return 0;
        }
        if (isPlayer()) {
            return equipment.getSave(damageType);
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
                ", health=" + health +
                ", height=" + height +
                ", inventory=" + inventory +
                ", lastToldBy=" + lastToldBy +
                ", learned=" + learned +
                ", mana=" + mana +
                ", moves=" + moves +
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
                ", immunityTackle=" + immunityTackle +
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
                ", isAware=" + isAware +
                ", isNoSummon=" + isNoSummon +
                ", isNoSleep=" + isNoSleep +
                ", isNoBash=" + isNoBash +
                ", isNoBlind=" + isNoBlind +
                ", returnRoom='" + returnRoom + '\'' +
                ", fallingCounter=" + fallingCounter +
                ", veryAggressive=" + veryAggressive +
                ", isMemory=" + isMemory +
                ", isPeekAggro=" + isPeekAggro +
                ", IDLE_TIMEOUT=" + IDLE_TIMEOUT +
                ", saves=" + saves +
                '}';
    }

    public Mob getCharmed() {
        return charmed;
    }

    public void setCharmed(Mob charmed) {
        this.charmed = charmed;
    }
}
