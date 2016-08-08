package com.ivstuart.tmud.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.behaviour.Wander;
import com.ivstuart.tmud.behaviour.Aggressive;
import com.ivstuart.tmud.behaviour.Sleeping;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.common.Gender;
import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.Learned;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.carried.Equipment;
import com.ivstuart.tmud.person.carried.Inventory;
import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.person.statistics.MobAffects;
import com.ivstuart.tmud.person.statistics.MobMana;

public class Mob extends Prop implements Tickable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(Mob.class);

	private String ability; // base mob

	private int align; // base mob
	private String armour;

	private String attackType; // base mob
	private int copper;
	private DiceRoll damage; // base mob
	private int defensive;
	private boolean immunityTackle = false;
	private transient boolean running = false;
	private int level; // base mob

	private DiceRoll maxHp; // base mob
	private MobAffects mobAffects;

	private transient MobStatus mobStatus;
	private String name; // immutable

	// TODO extract out static data for say n x snakes to reference 1 base snake
	// object
	// protected BaseMob _baseMob;

	private int offensive;
	private MobState state;
	private List<Tickable> tickers;
	private int xp;

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

	// TODO this should be transient for mobs and players?
	protected transient Player player;
	protected String race; // base mob
	protected String repopRoomID;

	protected Room room;

	protected int weight; // kg base mob

	private int wimpy;

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
		offensive = baseMob.offensive;
		defensive = baseMob.defensive;
		copper = baseMob.copper;
		xp = baseMob.xp;

		attackType = baseMob.attackType;
		ability = baseMob.ability;

		gender = baseMob.gender;
		race = baseMob.race;

		height = baseMob.height;
		weight = baseMob.weight;

		room = baseMob.room;

		repopRoomID = baseMob.repopRoomID;

	}

	public void addAffect(Affect affect_) {
		if (null == mobAffects) {
			mobAffects = new MobAffects(this);
		}
		mobAffects.add(affect_);
	}

	public MobAffects getMobAffects() {
		if (null == mobAffects) {
			mobAffects = new MobAffects(this);
		}
		return mobAffects;
	}

	public void addTickable(Tickable ticker) {
		if (tickers == null) {
			// TODO fix this
			tickers = new ArrayList<Tickable>();
			LOGGER.debug("Setting behaviours [ " + ticker + " ]");
			WorldTime.addTickable(this);
		}
		tickers.add(ticker);

	}

	public DiceRoll getDamage() {
		return damage;
	}

	public int getDefence() {
		return defensive;
	}

	public Equipment getEquipment() {
		if (equipment == null) {
			equipment = new Equipment();
		}
		return equipment;
	}

	public Fight getFight() {
		// TODO change this.
		if (fight == null) {
			fight = new Fight(this);
		}
		return fight;
	}

	public Mob getFollowing() {
		return following;
	}

	@Override
	public Gender getGender() {
		// TODO Auto-generated method stub
		return gender;
	}

	public Attribute getHp() {
		return health;
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

	public Learned getLearned() {
		if (learned == null) {
			learned = new Learned();
		}
		return learned;
	}

	public MobMana getMana() {
		return mana;
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

	@Override
	public String getName() {
		return name;
	}

	public int getOffensive() {
		return offensive;
	}

	public Player getPlayer() {
		return player;
	}

	public String getRace() {
		return race;
	}

	public String getRepopRoomId() {
		return repopRoomID;
	}

	public Room getRoom() {
		return room;
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

	public int getWeight() {
		return weight;
	}

	public int getXp() {
		return xp;
	}

	@Override
	public boolean hasDetectHidden() {
		return true;
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

	public boolean isGuarding(String id) {
		return false;
	}

	public boolean isImmunityTackle() {
		return immunityTackle;
	}

	public boolean isPlayer() {
		return (player != null);
	}

	public boolean isRunning() {
		return running;
	}

	@Override
	public String look() {

		return super.look() + "\n Hp=" + health.getValue();
	}

	@Override
	public void out(Msg msg_) {
		LOGGER.debug(this.getName()+" output [ " + msg_.toString(this) + " ]");
		if (player != null) {
			player.out(msg_.toString(this));
		}

	}

	public void out(String message) {
		LOGGER.debug(this.getName()+" output [ " + message + " ]");
		
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

	public void setArmour(String armour_) {
		this.armour = armour_;
	}

	public void setAttackType(String types) {
		this.attackType = types;
	}

	public void setBehaviour(String behaviours) {

		// Could pass in the class name and use reflection to instantiate
		if (behaviours.indexOf("WANDER") > -1) {

			addTickable(new Wander(this));
		}
		if (behaviours.indexOf("AGGR") > -1) {

			addTickable(new Aggressive(this));
		}
		if (behaviours.indexOf("SLEEP") > -1) {

			addTickable(new Sleeping(this));
		}

	}

	public void setCopper(String copper_) {
		this.copper = Integer.parseInt(copper_);
	}

	public void setDamage(DiceRoll damage) {
		this.damage = damage;
	}

	public void setDamage(String damage_) {
		this.damage = new DiceRoll(damage_);
	}

	public void setDefensive(String defensive_) {
		this.defensive = Integer.parseInt(defensive_.trim());
	}

	public void setFollowing(Mob mob_) {
		following = mob_;
	}

	public void setGender(Gender g) {
		gender = g;
	}

	public void setGender(String gender_) {
		gender = Gender.valueOf(gender_.toUpperCase());
	}

	public void setHeight(int height_) {
		height = height_;
	}

	public void setHp(String hp_) {
		maxHp = new DiceRoll(hp_);
		health = new Attribute("Health", maxHp.roll());
	}

	public void setImmunityTackle(boolean immunityTackle) {
		this.immunityTackle = immunityTackle;
	}

	public void setLastToldBy(Mob toldBy_) {
		lastToldBy = toldBy_;
	}

	public void setLevel(int level_) {
		this.level = level_;
	}

	public void setMana(MobMana mana_) {
		mana = mana_;
	}

	public void setMv(String moves_) {
		moves = new Attribute("Move", Integer.parseInt(moves_));
	}

	public void setName(String name_) {
		name = name_;
	}
	
	// TODO FIXME sort out if id and name is required for Mobs
	//  i.e is id, short and long descriptions sufficient.
	public void setNameAndId(String name_) {
		name = name_;
		this.setId(name_);
	}

	public void setOffensive(String offensive_) {
		this.offensive = Integer.parseInt(offensive_.trim());
	}

	public void setPlayer(Player player_) {
		player = player_;
	}

	public void setRace(String race_) {
		race = race_;
	}

	public void setRepopRoomId(String repopRoomId_) {
		repopRoomID = repopRoomId_;
	}

	public void setRoom(Room room_) {
		room = room_;

	}

	public void setRunning(boolean runFlag) {
		running = runFlag;
	}

	public void setState(MobState state_) {
		LOGGER.debug("You set state to " + state_.name());
		state = state_;
	}

	public void setState(String state_) {
		state = MobState.getMobState(state_);
	}

	public void setWeight(int weight_) {
		weight = weight_;
	}

	public void setXp(String xp_) {
		// _stats.add(new IntegerAttribute(XP,xp_));
		this.xp = Integer.parseInt(xp_);
	}

	public String showMobAffects() {
		if (mobAffects != null) {
			return mobAffects.toString();
		}
		return "";
	}

	@Override
	public void tick() {

		tickRegenerate();

		// TODO Check affects

		if (tickers != null) {
			for (Tickable ticker : tickers) {
				ticker.tick();
			}
		}

	}

	private void tickRegenerate() {

		if (health != null) {
			health.increase(1);
		}
		if (moves != null) {
			moves.increase(1);
		}
		if (mana != null) {
			mana.increase(1);
		}

		// int con = this.player.getAttributes().getCON().getValue();
		// _moves.increase(_state.getMoveMod());
		// _state.getManaMod());

		if (null != mobAffects) {
			mobAffects.tick();
		}

		// out("tick");

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

	public void setWimpy(int wimpy) {
		this.wimpy = wimpy;
	}

}
