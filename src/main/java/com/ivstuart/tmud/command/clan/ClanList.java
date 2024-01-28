/*
 *  Copyright 2024. Ivan Stuart
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


package com.ivstuart.tmud.command.clan;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.Clan;
import com.ivstuart.tmud.world.Clans;

public class ClanList extends BaseCommand {

    public ClanList() {
        super();
    }

    @Override
    public void execute(Mob mob, String input) {

        mob.out("Clan list:");

        int counter = 0;
        for (Clan clan : Clans.getClans()) {
            mob.out(counter + ". Clan name [" + clan.getName() + "] and leader " + clan.getLeader());
            counter++;
        }
    }

}
