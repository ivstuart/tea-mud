/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.FileHandle;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Calendar;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Bug extends BaseCommand {

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 *
	 */
	@Override
	public void execute(Mob mob, String input) {
		synchronized (Bug.class) {
			World.out("BUG:" + input, mob.isGood());
			String path = LaunchMud.mudServerProperties.getProperty("player.save.dir");
			FileHandle fh = new FileHandle(path + "bug-report.txt");
			Calendar c = Calendar.getInstance();

			try {
				fh.write(c.getTime() + "\n" + input + "\n");
				fh.close();
			} catch (IOException e) {
				LOGGER.error("Problem writing bug to bugreport file", e);
			}
		}
	}

}
