/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

import java.util.*;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TopTen extends BaseCommand {

    @Override
    public void execute(Mob mob_, String input) {

        mob_.out("$HTopTen~$J");

        SortedMap<Integer, String> ratingAndNames = new TreeMap<>(Collections.reverseOrder());


        for (String playerName : World.getPlayers()) {
            Mob mob = World.getPlayer(playerName.toLowerCase()).getMob();

            int rating = Rating.getRating(mob);

            ratingAndNames.put(rating, playerName);

        }

        Iterator<Map.Entry<Integer, String>> mapIter = ratingAndNames.entrySet().iterator();

        for (int i = 0; (i < 10); i++) {
            if (!mapIter.hasNext()) {
                break;
            }
            Map.Entry<Integer, String> entry = mapIter.next();
            mob_.out(entry.getKey() + " " + entry.getValue());
        }

        mob_.out("$H~$J");

    }

}
