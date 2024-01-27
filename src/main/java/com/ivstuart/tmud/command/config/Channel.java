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

package com.ivstuart.tmud.command.config;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.config.ChannelEnum;
import com.ivstuart.tmud.state.Mob;

public class Channel extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        execute(mob.getPlayer(), input);

    }

    public void execute(Player mob, String input) {

        if (input.isEmpty()) {
            mob.out(mob.getConfig().getChannelData().toString());
            return;
        }

        ChannelEnum channelEnum;

        try {
            channelEnum = ChannelEnum.valueOf(input.toUpperCase());
        }
        catch (IllegalArgumentException iae) {
            mob.out("No such channel to toggle:"+input);
            return;
        }

        mob.getConfig().getChannelData().flip(channelEnum);
        mob.out("Toggling channel "+input);

    }
}