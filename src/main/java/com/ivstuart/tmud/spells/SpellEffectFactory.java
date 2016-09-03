package com.ivstuart.tmud.spells;

import java.util.HashMap;
import java.util.Map;

public class SpellEffectFactory {

	public final static SpellEffect DAMAGE = new Damage();
	private static Map<String, SpellEffect> spellEffectMap = new HashMap<String, SpellEffect>();

	static {
		spellEffectMap.put("HEAL", new Heal());
		spellEffectMap.put("POISON", new Poison());
		spellEffectMap.put("CURE", new CurePoison());
		spellEffectMap.put("ARMOUR", new Protection());
		spellEffectMap.put("BLUR", new Blur());
		spellEffectMap.put("SANC", new Sanctury());
		spellEffectMap.put("RECALL", new Recall());
		spellEffectMap.put("SUMMON", new Summon());
		spellEffectMap.put("BUFF", new BuffStats());
        spellEffectMap.put("FLY", new Levitate());
        spellEffectMap.put("DETECT", new DetectInvisible());
        spellEffectMap.put("INVIS", new BuffStats());
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
		spellEffectMap.put("BLINDNESS", new Blindness());
		spellEffectMap.put("CURE-BLINDNESS", new CureBlindness());
		spellEffectMap.put("ANIMATE-DEAD", new AnimateDead());

		spellEffectMap.put("CHARM", new BuffStats());
		spellEffectMap.put("LOCATE", new LocateObject());
	}

	public static SpellEffect get(String spell) {

		return spellEffectMap.get(spell.toUpperCase());
	}

}
