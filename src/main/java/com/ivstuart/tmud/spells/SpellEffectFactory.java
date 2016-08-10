package com.ivstuart.tmud.spells;

import java.util.HashMap;
import java.util.Map;

// This is crap should make this another map in world and add types using the reflection tech
// I have already built....

public class SpellEffectFactory {

	private static Map<String, SpellEffect> spellEffectMap = new HashMap<String, SpellEffect>();

	public final static SpellEffect DAMAGE = new Damage();

	static {
		spellEffectMap.put("HEAL", new Heal());
		spellEffectMap.put("POISON", new Poison()); // TODO Add poison type
		spellEffectMap.put("CURE", new CurePoison());
		spellEffectMap.put("ARMOUR", new Protection());
		spellEffectMap.put("BLUR", new Blur());
		spellEffectMap.put("SANC", new Sanctury());
		spellEffectMap.put("RECALL", new Recall());
		spellEffectMap.put("SUMMON", new Summon());
		spellEffectMap.put("BUFF", new BuffStats());
		spellEffectMap.put("DETECT", new DetectInvisible());
		spellEffectMap.put("INVISIBLITY", new BuffStats());
		spellEffectMap.put("DEBUFF", new BuffStats());
		spellEffectMap.put("SLEEP", new Sleep());
		spellEffectMap.put("REFRESH", new Refresh());
		spellEffectMap.put("IDENT", new Identify());
		spellEffectMap.put("CREATE-LIGHT", new CreateLight());
		spellEffectMap.put("CREATE-FOOD", new CreateFood());
		spellEffectMap.put("CREATE-WATER", new CreateWater());
		spellEffectMap.put("ENCHANT", new EnchantWeapon());
		spellEffectMap.put("CONTROL-WEATHER", new ControlWeather());
		spellEffectMap.put("CLONE", new CloneMob());
		spellEffectMap.put("CURE-BLINDNESS", new CureBlindness());
		spellEffectMap.put("ANIMATE-DEAD", new AnimateDead());
	}

	public static SpellEffect get(String spell) {

		return spellEffectMap.get(spell.toUpperCase());
	}

}
