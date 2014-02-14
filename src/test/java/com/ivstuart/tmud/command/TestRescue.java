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
import com.ivstuart.tmud.state.World;

public class TestRescue {

	@Test
	public void testRescueWhenNotFighting() {

		Mob player1Mob = new Mob();
		Player player1 = new Player();
		player1.setMob(player1Mob);
		player1Mob.setNameAndId("player1");
		player1Mob.setPlayer(player1);

		Mob sheepMob = new Mob();
		sheepMob.setNameAndId("sheep");

		Mob player2Mob = new Mob();
		player2Mob.setNameAndId("player2");
		Player player2 = new Player();
		player2.setMob(player2Mob);
		player2Mob.setPlayer(player2);

		Room whiteRoom = new Room();

		whiteRoom.add(sheepMob);
		whiteRoom.add(player1Mob);
		whiteRoom.add(player2Mob);

		Command kill = CommandProvider.getCommand(Kill.class);

		assertEquals("Check sheep name", "sheep", sheepMob.getName());
		assertNotNull("Check sheep exists in the room",
				whiteRoom.getMob(sheepMob.getName()));
		assertEquals("Check sheep is in the room", sheepMob,
				whiteRoom.getMob(sheepMob.getName()));

		World.getInstance(); // Starts time.

		kill.execute(player1Mob, sheepMob.getName());
		kill.execute(sheepMob, player1Mob.getName());

		// Check they are targeting each other.
		assertEquals("sheep should target player1", player1Mob, sheepMob
				.getFight().getTarget());
		assertEquals("player 1 should target sheep", sheepMob, player1Mob
				.getFight().getTarget());

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("sheep and player1 will be engaged in combat", sheepMob
				.getFight().isEngaged(player1Mob));

		Command rescue = CommandProvider.getCommand(Rescue.class);
		rescue.execute(player2Mob, "player");

		assertFalse("sheep should target other player", sheepMob.getFight()
				.isEngaged(player1Mob));

		assertTrue("sheep should target other player", sheepMob.getFight()
				.isEngaged(player2Mob));

	}

	public void sleepAsecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
