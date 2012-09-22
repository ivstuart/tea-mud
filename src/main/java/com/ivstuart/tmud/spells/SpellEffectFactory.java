package com.ivstuart.tmud.spells;

import java.util.HashMap;
import java.util.Map;

// This is crap should make this another map in world and add types using the reflection tech
// I have already built....

public class SpellEffectFactory {

	private static Map<String, SpellEffect> _spellEffectMap = new HashMap<String, SpellEffect>();

	public final static SpellEffect DAMAGE = new Damage();

	static {
		_spellEffectMap.put("HEAL", new Heal());
		_spellEffectMap.put("POISON", new Poison());
		_spellEffectMap.put("CURE", new Cure());
	}

	public static SpellEffect get(String name_) {

		return _spellEffectMap.get(name_.toUpperCase());
	}

}
