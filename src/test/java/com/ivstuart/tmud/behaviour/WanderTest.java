package com.ivstuart.tmud.behaviour;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.World;

public class WanderTest {

	@Test
	public void testMoveRandomly() {
	
		Mob mob = new Mob();
		mob.setNameAndId("wanderingMob");
		
		// TODO create a set of rooms and exits for the Mob to be tested in 
		// OR load default test world and use the state from that in which to test (more of an integration test then)
		
		// TODO LaunchMud.main(new String[0]); // TODO set thread to exit when test does.
		
		
		Wander wandering = new Wander(mob);
		
		wandering.tick();
		wandering.tick();
		wandering.tick();
		wandering.tick();
		
		System.out.println("Rooms been to"+wandering.getRooms());
		
		// TODO LaunchMud.stop();

	}

	@Test
	public void testMoveWithinMaxDistanceOfStartPosition() {
		fail("Not yet implemented");
	}
	
}
