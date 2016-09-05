package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.world.WeatherSky;

public class ControlWeather implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		// Note pass in parameter for better or worse weather
		WeatherSky sky = World.getWeather().getNextSky(1000);

		World.setWeather(sky);

		caster_.out("The weather changes");
		caster_.out(sky.getDesc());
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
