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

package com.ivstuart.tmud.command.misc;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.server.Readable;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * <p>
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Password extends BaseCommand {

    @Override
    public void execute(Mob mob, String input) {

        mob.out("Enter current password:");

        Readable playing = mob.getPlayer().getConnection().getState();

        mob.getPlayer().getConnection().setState(new PasswordReadable(mob, playing));

    }

    private class PasswordReadable implements Readable {

        private final Mob mob;
        private final Readable playing;


        public PasswordReadable(Mob mob, Readable playing) {
            this.mob = mob;
            this.playing = playing;
        }

        @Override
        public void read(String line) {

            // Check if matches.

            if (!mob.getPlayer().getData().isPasswordSame(line)) {
                mob.out("Your password entered did not match!");
                mob.getPlayer().getConnection().setState(playing);
                return;
            }

            mob.out("Enter your new password.");

            mob.getPlayer().getConnection().setState(new NewPasswordReadable(mob, playing));

        }
    }

    private class NewPasswordReadable implements Readable {
        private final Mob mob;
        private final Readable playing;

        public NewPasswordReadable(Mob mob, Readable playing) {
            this.mob = mob;
            this.playing = playing;
        }

        @Override
        public void read(String line) {

            mob.getPlayer().getData().setPassword(line);
            mob.getPlayer().getConnection().setState(playing);

            mob.out("Your password has been updated successfully.");

            // SavePlayer
            Command command = new SavePlayer();
            command.execute(mob, null);
        }
    }
}
