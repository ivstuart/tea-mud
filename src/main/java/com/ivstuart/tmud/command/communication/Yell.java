/*
 * Created on 22-Sep-2003
 *
 */
package com.ivstuart.tmud.command.communication;

import java.util.LinkedList;
import java.util.List;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

/**
 * @author stuarti
 * 
 */
public class Yell implements Command {

	private List<Room> area;

	private static final int range = 5;

	@Override
	public void execute(Mob mob, String input) {

		Yell yell = new Yell();

		yell.area = new LinkedList<Room>();

		yell.area.add(mob.getRoom()); // Include room presently in

		yell.findArea(mob.getRoom().getExits(), 0);

		yell.yellArea(mob.getId() + " yells, \"" + input + "\"");

		yell.area.clear(); // Prevent memory leak
		yell.area = null;
		yell = null;

	}

	private void findArea(List<Exit> exits, int depth) {

		if (depth > range) {
			return;
		}

		for (Exit exit : exits) {
			Room room = exit.getDestinationRoom();
			if (!area.contains(room)) {
				area.add(room);
				this.findArea(room.getExits(), ++depth);
			}
		}
	}

	private void yellArea(String message) {
		for (Room aRoom : area) {
			aRoom.out(message);
		}

	}

}
