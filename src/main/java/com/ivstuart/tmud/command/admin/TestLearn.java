/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestLearn implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		mob.out("Your slashing skill is currently "
				+ mob.getLearned().getAbility("slashing"));

		Ability ab1 = new Ability("slashing");
		Ability ab2 = new Ability("piercing");
		Ability ab3 = new Ability("crushing");
		Ability ab4 = new Ability("chopping");
		Ability ab5 = new Ability("thrusting");
		
		Ability ab6 = new Ability("search");

		ab1.setSkill(20);
		ab2.setSkill(30);
		ab3.setSkill(40);
		ab4.setSkill(50);
		ab5.setSkill(90);
        ab6.setSkill(90);

		learn(mob, ab1);
		learn(mob, ab3);
		learn(mob, ab4);
		learn(mob, ab5);
		learn(mob, ab6);

		learnSkill(mob, "kick");
		learnSkill(mob, "rescue"); // 19th Jul 2009

		learnSkill(mob, "tackle"); // 25th Jul 2009

		Ability bash = new Ability("bash");
		bash.setSkill(50);
		learn(mob, bash);

		Ability magicMissile = new Ability("magic missile");
		magicMissile.setSkill(90);
		mob.getLearned().add(magicMissile);

		Ability shock = new Ability("shock");
		shock.setSkill(90);
		mob.getLearned().add(shock);

		mob.out("You learn slashing crushing chopping thrusting - cool");

	}

	private void learn(Mob mob, Ability ability) {

		if (mob.getLearned().hasLearned(ability.getId())) {
			mob.out("You already know " + ability.getId());

		} else {
			mob.getLearned().add(ability);
			mob.out("You learn " + ability.getId());
		}
	}

	private void learnSkill(Mob mob, String name) {
		Ability ability = new Ability(name);
		ability.setSkill(100);
		learn(mob, ability);
	}

}
