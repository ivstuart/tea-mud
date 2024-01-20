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

package com.ivstuart.tmud.command;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 14/08/2016.
 */
public class Social extends BaseCommand {

    private final String cmd;
    private String description;

    public Social(String line) {
        this.cmd = line;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    @Override
    public void execute(Mob mob, String input) {

        Mob target = mob.getRoom().getMobs().get(input);

        Msg msg = new Msg(mob, target, "<S-NAME> " + cmd + "'s " + description);

        mob.getRoom().out(msg);
    }

    @Override
    public String getHelp() {

        return "The command " + cmd + " is a social commands for communication only";
    }
}
