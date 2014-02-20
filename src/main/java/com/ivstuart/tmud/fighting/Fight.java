/*
 * Created on 24-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.ivstuart.tmud.fighting.action.BasicAttack;
import com.ivstuart.tmud.fighting.action.FightAction;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.WorldTime;

/**
 * @author Ivan Stuart
 */
public class Fight {

	private static final int MAX_SIZE = 10;
	
	private static final Logger LOGGER = Logger.getLogger(Fight.class);

	// TODO use a PriorityQueue ?
	private LinkedList<FightAction> fightActions;

	private FightAction melee;

	private List<Mob> targettedBy;

	/**
	 * 
	 */
	public Fight(FightAction melee) {
		this.melee = melee;
		fightActions = new LinkedList<FightAction>();
		targettedBy = new LinkedList<Mob>();
	}

	public Fight(Mob mob) {
		this(new BasicAttack(mob, null));
	}

	public void add(FightAction action) {

		if (fightActions.size() > MAX_SIZE) {
			out("Ignoring command as too many have been queued.");
			return;
		}

		// i.e. for bash want to start the combat.
		if (fightActions.isEmpty()) {
			if (melee.getTarget() == null) {
				melee.setTarget(action.getTarget());
			}
		}

		fightActions.add(action);

		WorldTime.addFighting(melee.getSelf());
	}

	private boolean addTargettedBy(Mob mob) {
		return targettedBy.add(mob);
	}

	public void changeTarget(Mob newTargetMob) {

		Mob ownTarget = melee.getTarget();

		if (ownTarget != null) {
			ownTarget.getFight().removeTargettedBy(melee.getSelf());
			
			LOGGER.debug(ownTarget.getName()+" target removed from "+melee.getSelf().getName());
		}

		melee.setTarget(newTargetMob);

		if (newTargetMob != null) {

			newTargetMob.getFight().addTargettedBy(melee.getSelf());
			
			LOGGER.debug(newTargetMob.getName()+" is changing target to "+melee.getSelf().getName());

		}

	}

	/**
	 * 
	 */
	public void clear() {
		targettedBy.clear();
		fightActions.clear();

	}

	public FightAction getMelee() {
		return melee;
	}

	public Mob getSelf() {
		return melee.getSelf();
	}

	public Mob getTarget() {
		return melee.getTarget();
	}

	public List<Mob> getTargettedBy() {
		return targettedBy;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEngaged() {
		return !targettedBy.isEmpty();
	}

	public boolean isEngaged(Mob combatent) {
		return targettedBy.contains(combatent);
	}

	// TODO api
	// TODO how to track who to fight if they go to another room
	public boolean isFighting() {
		return melee.getTarget() != null;
	}

	public boolean isGroundFighting() {
		return melee.isGroundFighting();
	}

	private void out(String message) {
		melee.getSelf().out(message);
	}

	public FightAction remove() {
		return fightActions.remove();
	}

	private boolean removeTargettedBy(Mob mob) {
		return targettedBy.remove(mob);
	}

	public void resolveCombat() {

		if (fightActions.isEmpty()) {

			// Log.info("Fight action is empty foe "+this.getMelee().getSelf().getName());

			resolveMelee();

		} else {

			FightAction fightAction = fightActions.getFirst();

			if (fightAction.isFinished()) {
				fightActions.remove();
			}

			if (fightAction.isMeleeEnabled()) {
				resolveMelee();

			}

			fightAction.next();

		}

	}

	private void resolveMelee() {

		// Log.info("resolveMelee "+this.getMelee().getSelf().getName()+" is targeting "+melee.getTarget().getName());

		if (melee.getTarget() != null) {
			melee.next();
			if (melee.isFinished()) {
				melee.restart();
			}
		}
	}

	public void setMelee(FightAction melee) {
		this.melee = melee;
	}

	public void setMeleeToBasicAttack() {
		this.melee = new BasicAttack(melee.getSelf(), melee.getTarget());
	}

	public boolean stopFighting() {

		if (melee.getTarget() == null) {
			return false;
		}

		changeTarget(null);

		return true;
	}

	@Override
	public final String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean isBeingCircled() {
		for (Mob mob : targettedBy) {
			if (mob.getMobStatus().isCircling()) {
				return true;
			}
		}
		return false;
	}

}