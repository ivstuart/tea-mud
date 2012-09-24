package com.ivstuart.tmud.state.util;

import static com.ivstuart.tmud.constants.DoorState.CLOSED;
import static com.ivstuart.tmud.constants.DoorState.LOCKED;
import static com.ivstuart.tmud.constants.DoorState.OPEN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.constants.DoorState;
import com.ivstuart.tmud.state.Door;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.state.dao.DoorDAO;

public class RoomManager {

	private static final Logger LOGGER = Logger.getLogger(RoomManager.class);

	private static Map<String, String> _directionMap = new HashMap<String, String>();

	// Not thread safe but we only single thread loading of the world
	private static Door _lastCreatedDoor;

	private static List<DoorDAO> exitsWithDoors;

	static {
		new RoomManager();
	}

	public static void createDoors(String roomId_, String exit_) {

		LOGGER.info("Creating door in room [ " + roomId_ + " ] for [ " + exit_
				+ " ]");

		DoorState state = OPEN;

		if (exit_.indexOf("[") > -1) {
			exit_ = exit_.substring(1, exit_.length() - 1);
			state = CLOSED;
		} else if (exit_.indexOf("{") > -1) {
			exit_ = exit_.substring(1, exit_.length() - 1);
			state = LOCKED;
		}

		Door aDoor = new Door();

		aDoor.setLook("gate");
		aDoor.setState(state);

		_lastCreatedDoor = aDoor;

		DoorDAO doorDAO = new DoorDAO();
		doorDAO.setDoor(aDoor);
		doorDAO.setExit(exit_);
		doorDAO.setRoom(roomId_);

		exitsWithDoors.add(doorDAO);

	}

	/**
	 * @param string
	 */
	public static void createExit(Room from, String direction, String toRoomId) {
		createExit(from, direction, toRoomId, false);

	}

	/**
	 * @param string
	 */
	public static void createExit(Room from, String direction, String toRoomId,
			boolean isOneWay) {

		Exit exitFromTo = new Exit(direction, toRoomId);
		from.add(exitFromTo);

		if (isOneWay) {
			return;
		}

		Exit exitToFrom = new Exit(reverseDirection(direction), from.getId());

		World.getRoom(toRoomId).add(exitToFrom);

	}

	public static void createExits(Room tmpRoom, String exitStringList) {

		for (String exit : exitStringList.split(" ")) {

			String[] pair = exit.split("->");

			// Guard condition
			if (pair.length < 2) {
				continue;
			}

			createExit(tmpRoom, pair[0], pair[1], true);
		}
	}

	public static void main(String arg[]) {
		System.out.println("East becomes "
				+ RoomManager.reverseDirection("east"));
	}

	public static String reverseDirection(String direction) {

		String reversed = _directionMap.get(direction);

		LOGGER.debug("direction " + direction + " becomes " + reversed);

		return (reversed != null ? reversed : direction);

	}

	public static void setDoorKeys(String keys_) {
		if (_lastCreatedDoor == null) {
			return;
		}

		_lastCreatedDoor.setId(keys_);
	}

	public static void setDoorOnEndOfExit() {

		for (DoorDAO door : exitsWithDoors) {

			LOGGER.debug("Setting up [" + door + "]");

			Room room = World.getRoom(door.getRoom());

			Exit exit = room.getExit(door.getExit());

			exit.setDoor(door.getDoor());

			Exit opExit = exit.getDestinationRoom().getExit(
					RoomManager.reverseDirection(door.getExit()));

			if (opExit != null) {
				opExit.setDoor(exit.getDoor());
			}
		}

		exitsWithDoors.clear();
	}

	public RoomManager() {
		// TODO enum to replace this?
		_directionMap.put("north", "south");
		_directionMap.put("east", "west");
		_directionMap.put("south", "north");
		_directionMap.put("west", "east");
		_directionMap.put("up", "down");
		_directionMap.put("down", "up");

		exitsWithDoors = new ArrayList<DoorDAO>();
	}
}
