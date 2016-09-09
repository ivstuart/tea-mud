/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WeatherInfo extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

//		if (mob.getRoom().getSectorType().isInside()) {
//			mob.out("You are indoors and the air is still");
//			return;
//		}

        mob.out("The weather is " + World.getWeather().toString().toLowerCase());
    }

}
