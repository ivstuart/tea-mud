package com.ivstuart.tmud.constants;

public class AbilityConstants {

	// **** [You have become better at <SKILL>!] ****

	public static String abilityString[] = { "untrained", "unskilled",
			"pathetic", "bad", "poor", "mediocre", "some skill", "fine",
			"competent", "proficient", "capable", "good", "skilled",
			"talented", "masterful", "gifted", "expert", "excellent",
			"amazing", "godly" };

	public static final String SHIELD_BLOCK = "shield block";

	public static String getSkillString(int skill_) {
		int index = (skill_ * abilityString.length) / 100;

		if (index >= abilityString.length) {
			index = abilityString.length - 1;
		}

		return abilityString[index];
	}
}
