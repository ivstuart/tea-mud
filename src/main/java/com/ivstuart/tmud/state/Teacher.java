/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.world.World;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Teacher extends Prop {

	private List<String> _abilities;

	public Teacher() {
		_abilities = new ArrayList<String>();
	}

	public Teacher(Teacher teacher_) {
		super(teacher_);

		_abilities = teacher_._abilities;
	}

	public String displayAbilities() {

		StringBuilder sb = new StringBuilder();

		if (teachesEverything()) {

			for (BaseSkill skill : World.getSkills().values()) {

				sb.append(skill.getId() + "\n");

			}

			for (Spell spell : World.getSpells().values()) {

				sb.append(spell.getId() + "\n");

			}

			return sb.toString();
		} else {
			for (Object obj : _abilities) {
				sb.append(obj.toString()).append("\n");
			}
		}
		return sb.toString();
	}

	public String getAbility(String ability_) {

		for (String ability : _abilities) {
			if (ability.startsWith(ability_)) {
				return ability;
			}
		}
		return null;
	}

	@Override
	public boolean isTeacher() {
		return true;
	}

	public void setAbility(String ability_) {
		_abilities.add(ability_);
	}

	public boolean teachesEverything() {
		return (_abilities.isEmpty());
	}
}
