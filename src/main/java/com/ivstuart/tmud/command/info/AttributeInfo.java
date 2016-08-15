/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.common.Info;
import com.ivstuart.tmud.state.Attributes;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttributeInfo extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		mob.out(Info.BAR);

		Attributes att = mob.getPlayer().getAttributes();

		mob.out(att.getSTR().getDescription());
		mob.out(att.getCON().getDescription());
		mob.out(att.getDEX().getDescription());
		mob.out(att.getINT().getDescription());
		mob.out(att.getWIS().getDescription());

		mob.out(Info.BAR);

	}

}
