package com.ivstuart.tmud.command.info;

import java.util.List;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.utils.MudArrayList;

public class Scan implements Command {

	private Mob myMob;

	private static final String DISTANCE[] = { "is pretty close by",
			"is close by", "is not far off", "is a long way off",
			"is very distant" };

	@Override
	public void execute(Mob mob, String input) {

		Scan scanner = new Scan();

		scanner.myMob = mob;

		scanner.out("You look all around you...");

		scanner.scan(mob.getRoom().getExits());

		scanner = null; // Probably would not have to do this.

	}

	private void out(String message) {
		myMob.out(message);
	}

	private void scan(Exit exit, int distance) {
		if (distance > DISTANCE.length || exit == null) {
			return;
		}
		showCharacters(exit.getDestinationRoom().getMobs(), distance, exit);
		scan(exit.getDestinationRoom().getExit(exit.getId()), ++distance);
	}

	private void scan(List<Exit> exits) {
		for (Exit exit : exits) {
			if (exit.isScanable()) {
				scan(exit, 0);
			}
		}
	}

	private void showCharacters(MudArrayList<Mob> mobs, int distance, Exit exit) {
		for (Mob mob : mobs) {
			out(mob.getName() + " " + DISTANCE[distance] + " to the "
					+ exit.getId() + "\n");
		}
	}

}
