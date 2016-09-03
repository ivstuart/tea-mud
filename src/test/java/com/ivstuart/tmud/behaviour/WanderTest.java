package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import org.junit.Test;

import static org.junit.Assert.fail;

public class WanderTest {

	/**
     *
     */
	@Test
	public void testMoveRandomly() {

		Mob mob = new Mob();
		mob.setNameAndId("wanderingMob");

        LaunchMud.main(new String[0]);

		// See MudConnectionTest and create a utility
		// TestHelper.loadMudServerInstance(); // optionally with a timeout

		Wander wandering = new Wander();

		wandering.tick();
		wandering.tick();
		wandering.tick();
		wandering.tick();

		// Confirm sheep has moved.

		System.out.println("Rooms been to" + wandering.getRooms());


    }

	/**
     *
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
