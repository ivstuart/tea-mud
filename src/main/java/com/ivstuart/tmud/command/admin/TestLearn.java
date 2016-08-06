/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.BaseSkill;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestLearn extends AdminCommand {

	
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);
		
		mob.out("This is a test command to learn all skills and spells to 80% in the game");
		
		// Learn all skills
		for (BaseSkill skill : World.getSkills().values()) {
			Ability ability = new Ability(skill.getName());
			ability.setSkill(80);
			learn(mob, ability);
		}
		
		// Learn all skills
		for (Spell spell : World.getSpells().values()) {
			Ability ability = new Ability(spell.getName());
			ability.setSkill(80);
			learn(mob, ability);
		}

	}

	private void learn(Mob mob, Ability ability) {

		if (mob.getLearned().hasLearned(ability.getId())) {
			mob.out("You already know " + ability.getId());

		} else {
			mob.getLearned().add(ability);
			mob.out("You learn " + ability.getId());
		}
	}


}
