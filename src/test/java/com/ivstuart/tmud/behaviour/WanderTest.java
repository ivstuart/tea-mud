package com.ivstuart.tmud.behaviour;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;

public class WanderTest {

	/**
	 * TODO
	 */
	@Test
	public void testMoveRandomly() {

		Mob mob = new Mob();
		mob.setNameAndId("wanderingMob");

		// TODO create a set of rooms and exits for the Mob to be tested in
		// OR load default test world and use the state from that in which to
		// test (more of an integration test then)

		LaunchMud.main(new String[0]); // TODO set thread to exit when test
										// does.

		// See MudConnectionTest and create a utility
		// TestHelper.loadMudServerInstance(); // optionally with a timeout

		Wander wandering = new Wander(mob);

		// Reuse an existing mob or make a lost sheep mob.
		// Set it to have wandering behaviour

		// TestHelper.tick();;;;;

		wandering.tick();
		wandering.tick();
		wandering.tick();
		wandering.tick();

		// Confirm sheep has moved.

		System.out.println("Rooms been to" + wandering.getRooms());

	}

	/**
	 * TODO
	 * [ ]-[ ]-[ ]-[ start pos ]-[ ]-[ ]
	 * 
	 * Confirm that the lost sheep can get at most 2 rooms away from start
	 * position.
	 * 
	 */
	@Test
	public void testMoveWithinMaxDistanceOfStartPosition() {
		fail("Not yet implemented");
	}

}
