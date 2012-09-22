package com.ivstuart.tmud.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.combat.Kill;
import com.ivstuart.tmud.command.combat.Rescue;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

public class TestRescue {

	@Test
	public void testRescueWhenNotFighting() {

		Mob playerMob = new Mob();
		playerMob.setName("player");

		Player player1 = new Player();
		player1.setMob(playerMob);

		playerMob.setPlayer(player1);

		Mob dragon = new Mob();
		dragon.setName("dragon");

		Mob anotherPlayerMob = new Mob();
		anotherPlayerMob.setName("player2");

		Room whiteRoom = new Room();

		whiteRoom.add(dragon);
		whiteRoom.add(playerMob);
		whiteRoom.add(anotherPlayerMob);
		

		Command kill = CommandProvider.getCommand(Kill.class);
		
		assertEquals("Check dragon","dragon",dragon.getName());		
		
		// TODO fix this
		assertNotNull("Check dragon is in room",whiteRoom.getMob(dragon.getName()));
		
		assertEquals("Check dragon is in room",dragon,whiteRoom.getMob(dragon.getName()));

		kill.execute(playerMob, dragon.getName());
		kill.execute(dragon, playerMob.getName());


		assertTrue("Dragon should target player",
				dragon.getFight().isEngaged(playerMob));

		Command rescue = CommandProvider.getCommand(Rescue.class);
		rescue.execute(anotherPlayerMob, "player");

		assertFalse("Dragon should target other player", dragon.getFight()
				.isEngaged(playerMob));
		
		assertTrue("Dragon should target other player", dragon.getFight()
				.isEngaged(anotherPlayerMob));

	}

	public void sleepAsecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
