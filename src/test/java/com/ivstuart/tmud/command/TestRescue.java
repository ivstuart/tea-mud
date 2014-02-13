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

		Mob angrySheep = new Mob();
		angrySheep.setName("sheep");

		Mob anotherPlayerMob = new Mob();
		anotherPlayerMob.setName("player2");

		Room whiteRoom = new Room();

		whiteRoom.add(angrySheep);
		
		// TODO fix this
		assertNotNull("Check sheep is in room",whiteRoom.getMob(angrySheep.getName()));
		
		whiteRoom.add(playerMob);
		whiteRoom.add(anotherPlayerMob);
		

		Command kill = CommandProvider.getCommand(Kill.class);
		
		assertEquals("Check sheep","sheep",angrySheep.getName());		
		
		assertNotNull("Check sheep is in room",whiteRoom.getMob(angrySheep.getName()));
		
		assertEquals("Check sheep is in room",angrySheep,whiteRoom.getMob(angrySheep.getName()));

		kill.execute(playerMob, angrySheep.getName());
		kill.execute(angrySheep, playerMob.getName());


		assertTrue("sheep should target player",
				angrySheep.getFight().isEngaged(playerMob));

		Command rescue = CommandProvider.getCommand(Rescue.class);
		rescue.execute(anotherPlayerMob, "player");

		assertFalse("sheep should target other player", angrySheep.getFight()
				.isEngaged(playerMob));
		
		assertTrue("sheep should target other player", angrySheep.getFight()
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
