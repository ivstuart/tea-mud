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

package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.person.carried.SomeMoney;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.utils.MudArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.ivstuart.tmud.constants.SpellNames.BLINDNESS;
import static com.ivstuart.tmud.constants.SpellNames.INFRAVISION;

public class Look extends BaseCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    /*
     * > look > look AT the angel > look IN the bag > look south (May give some
     * information as to what is south)
     */
    @Override
    public void execute(Mob mob_, String input_) {

        if (mob_.getState().isSleeping()) {
            mob_.out("Can not look while sleeping zzzZZZZzz....");
            return;
        }

        if (mob_.getMobAffects().hasAffect(BLINDNESS)) {
            mob_.out("Can not not see anything while blinded!");
            return;
        }


        if (mob_.getRoom().isDark()) {
            // Can mob see in the dark?
            if (mob_.getMobAffects().hasAffect(INFRAVISION) || mob_.getRace().isInfravison()) {
                mob_.out("You can see in the dark");
            } else {
                // Is an item in the room which is creating light?

                // Is a mob in the room with a lit torch?

                if (!mob_.getRoom().hasLightSource()) {
                    mob_.out("Too dark...");
                    return;
                } else {
                    mob_.out("This dark room is illuminated.");
                }
            }

        }

        if (input_.length() > 0) {
            Prop prop = mob_.getRoom().getProps().get(input_);

            if (prop != null) {
                mob_.out("Prop info " + prop.look());

                if (mob_.getPlayer().isAdmin()) {
                    mob_.out("Prop info " + prop);
                }
            }

        }

        if (input_.length() > 0) {
            Mob mob = mob_.getRoom().getMob(input_);
            if (mob != null) {
                mob_.getRoom()
                        .out(new Msg(mob_, mob,
                                "<S-You/NAME> looks at <T-you/NAME>"));
                mob_.out(mob.getLook());
                mob_.out(mob.getEquipment().toString());

                if (mob.isPeekAggro()) {
                    Fight.startCombat(mob_, mob);
                }

                if (mob_.getPlayer().isAdmin()) {
                    mob_.out("Info mob id       = " + mob.getId());
                    mob_.out("Info mob repop id = " + mob.getRepopRoomId());
                    mob_.out("Type :" + mob_.getClass().getCanonicalName());
                }
            }
        }

        if (input_.length() > 0) {
            Item item = mob_.getInventory().get(input_);

            if (item != null) {

                mob_.out(item.look());

                if (mob_.getPlayer().isAdmin()) {
                    mob_.out("item = " + item);
                }
            }
        }

        if (input_.length() > 0) {
            Item item = mob_.getRoom().getInventory().get(input_);

            if (item != null) {

                mob_.out(item.look());

                if (mob_.getPlayer().isAdmin()) {
                    mob_.out("item = " + item);
                }
            }
            return;
        }

        showRoom(mob_);

        showExits(mob_);

        showProps(mob_);

        showTracks(mob_);

        showMobs(mob_);

        showItems(mob_);

        Prompt.show(mob_);

    }

    private void showExits(Mob mob) {

        StringBuilder sb = new StringBuilder("  $K[Exits: ");

        List<Exit> exits = mob.getRoom().getExits();

        if (exits.isEmpty()) {
            sb.append("none");
        } else {
            for (Exit exit : exits) {
                if (!exit.isHidden() || mob.hasDetectHidden()) {

                    String exitLook = exit.look();

                    if (mob.getRoom().isWater()) {
                        sb.append("<" + exitLook).append("> ");
                    } else {
                        sb.append(exitLook).append(" ");
                    }

                }

            }
        }

        sb.append("]$J");

        mob.out(sb.toString());
    }

    private void showItems(Mob mob) {

        SomeMoney sm = mob.getRoom().getInventory().getPurse();

        if (sm != null && !sm.isEmpty()) {
            mob.out(sm.toString());
        }

        MudArrayList<Item> list = mob.getRoom().getInventory().getItems();

        if (list.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < list.size(); index++) {
            Item item = list.get(index);
            if (!item.isHidden() || mob.hasDetectHidden()) {
                sb.append("$IA " + item.getLook() + " lies here.\n");
            }
        }
        if (sb.length() != 0) {
            sb.append("$J");
            mob.out(sb.toString());
        }
    }

    private void showMobs(Mob mob_) {
        StringBuilder sb = new StringBuilder();

        for (Mob mob : mob_.getRoom().getMobs()) {

            if (mob_.hasDetectHidden()) {
                if (mob.isHidden()) {
                    sb.append("(hidden)");
                }
            } else {
                if (mob.isHidden()) {
                    continue;
                }
            }

            if (mob_.hasDetectInvisible()) {
                if (mob.isInvisible()) {
                    sb.append("(invis)");
                }
            } else {
                if (mob.isInvisible()) {
                    continue;
                }
            }

            // Note this should be all done as part of Msg command
            if (mob.isPlayer()) {
                if (!mob.isAlignmentSame(mob_)) {
                    sb.append("$H +* " + mob.getSize() + " " + mob.getAge() + " " + mob.getRace().getName() + " *+");
                } else {
                    sb.append("$H" + mob.getBrief());
                }
            } else {
                sb.append("$H" + mob.getBrief());
            }

            if (mob.getFight() != null && mob.getFight().isFighting()) {
                sb.append(" is fighting a");
                Mob target = mob.getFight().getTarget();
                if (target != null) {
                    if (target == mob_) {
                        sb.append("you");
                    } else {
                        sb.append(target.getName() + "!");
                    }
                } else {
                    sb.append("someone!");
                }

            } else {
                if (mob.getState().isSleeping()) {
                    sb.append(" is sleeping here.");
                }
                if (mob.getState().isFlying()) {
                    sb.append(" is flying here.");
                } else {
                    sb.append(" is here.");
                }
            }
            sb.append("\n");
        }
        sb.append("$J");
        mob_.out(sb.toString());
    }

    private void showProps(Mob mob) {

        for (Prop p : mob.getRoom().getProps()) {
            mob.out(p.look());
        }

    }

    private void showRoom(Mob mob) {

        // Need to work out how to display weather.
        mob.out(mob.getRoom().getBrief() + " [" + mob.getRoom().getType() + "]");

        mob.out(mob.getRoom().getLook());

        /**
         * if
         * (mob.getPlayer().getConfig().getConfigData().is(ConfigData.VERBOSE))
         * { mob.out(mob.getRoom().getLook()); } else {
         * mob.out(mob.getRoom().getShort()); }
         */

    }

    private void showTracks(Mob mob) {
        List<Track> tracks = mob.getRoom().getTracks();

        for (Track track : tracks) {
            if (track.isBlood()) {
                mob.out("A blood trail leads off to the "
                        + track.getDirection());
            } else {
                mob.out("A faint trail leads off to the "
                        + track.getDirection());

            }
        }
    }

}