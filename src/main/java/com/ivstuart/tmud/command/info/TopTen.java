/*
 * Created on 23-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

import java.util.*;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TopTen extends BaseCommand {

	@Override
	public void execute(Mob mob_, String input) {

		mob_.out("$HTopTen~$J");

		SortedMap<Integer,String> ratingAndNames = new TreeMap<>(Collections.reverseOrder());


		for (String playerName : World.getPlayers()) {
			Mob mob = World.getPlayer(playerName.toLowerCase()).getMob();

			int rating = Rating.getRating(mob);

			ratingAndNames.put(rating,playerName);

		}

		Iterator<Map.Entry<Integer, String>> mapIter = ratingAndNames.entrySet().iterator();

		for (int i=0;(i<10);i++) {
			if (mapIter.hasNext()==false) {
				break;
			}
			Map.Entry<Integer,String> entry = mapIter.next();
			mob_.out(entry.getKey()+" "+entry.getValue());
		}

		mob_.out("$H~$J");

	}

}
