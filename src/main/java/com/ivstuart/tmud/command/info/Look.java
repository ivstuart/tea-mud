package com.ivstuart.tmud.command.info;

import static com.ivstuart.tmud.constants.RoomEnums.RoomFlag.DARK;

import java.util.List;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.GuardMob;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.state.Track;
import com.ivstuart.tmud.utils.MudArrayList;

public class Look implements Command {
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

		if (input_.length() > 0) {
			Mob mob = mob_.getRoom().getMob(input_);
			if (mob != null) {
				mob_.out("Info mob id       = " + mob.getId());
				mob_.out("Info mob repop id = " + mob.getRepopRoomId());
				mob_.out("Type :" + mob_.getClass().getSimpleName());
				mob_.out("instanceof GuardMob:" + (mob_ instanceof GuardMob));
			}
			return;
		}

		if (mob_.getRoom().hasProperty(DARK.toString())) {
			// Can mob see in the dark?

			// Is an item in the room which is creating light?

			// Is a mob in the room with a lit torch?

			if (!mob_.getRoom().hasLightSource()) {
				mob_.out("Too dark...");
				return;
			} else {
				mob_.out("This dark room is illuminated.");
			}

		}

		if (input_.length() > 0) {
			Mob mob = mob_.getRoom().getMob(input_);

			if (mob != null) {
				mob_.getRoom()
						.out(new Msg(mob_, mob,
								"<S-You/NAME> looks at <T-you/NAME>"));
				mob_.out(mob.look());
				return;
			}
		}

		showRoom(mob_);

		showExits(mob_);

		showProps(mob_);

		showTracks(mob_);

		showMobs(mob_);

		showCash(mob_);

		showItems(mob_);

		Prompt.show(mob_);

	}

	private void showCash(Mob mob) {

		// mob.out(mob.getRoom().toString());
	}

	private void showExits(Mob mob) {

		StringBuilder sb = new StringBuilder("  $K[Exits: ");

		List<Exit> exits = mob.getRoom().getExits();

		if (exits.isEmpty()) {
			sb.append("none");
		} else {
			for (Exit exit : exits) {
				if (!exit.isHidden() || mob.hasDetectHidden()) {
					sb.append(exit.look()).append(" ");
				}

			}
		}

		sb.append("]$J");

		mob.out(sb.toString());
	}

	private void showItems(Mob mob) {
		MudArrayList<Item> list = mob.getRoom().getItems();

		if (list.isEmpty()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < list.size(); index++) {
			sb.append("$IA " + list.get(index).getLook() + " lies here.\n");
		}
		sb.append("$J");
		mob.out(sb.toString());
	}

	private void showMobs(Mob mob_) {
		StringBuilder sb = new StringBuilder();

		for (Mob mob : mob_.getRoom().getMobs()) {

			if (mob_.hasDetectInvisible()) {
				if (mob.isInvisible()) {
					sb.append("(invis)");
				}
			}

			sb.append("$H" + mob.getBrief());



			// TODO should replace this code with a Msg object to handle the
			// correct tense of output.

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
				else {
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