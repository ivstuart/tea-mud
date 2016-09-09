/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command;

import com.ivstuart.tmud.command.combat.Kill;
import com.ivstuart.tmud.command.combat.Rescue;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.BaseSkill;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.utils.TestHelper;
import com.ivstuart.tmud.world.World;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRescue {

	@Test
	public void testRescueWhenNotFighting() {

		Mob player1Mob = TestHelper.makeDefaultPlayerMob("player1");
		Mob player2Mob = TestHelper.makeDefaultPlayerMob("player2");

		// Teach rescue to player 2
		Ability rescueAbility = new Ability("rescue", 100);
		player2Mob.getLearned().add(rescueAbility);
		World.add(new BaseSkill("rescue"));

        // have test resource file to load in a mob sheep and mob player
        // test files.
		Mob sheepMob = new Mob();
		sheepMob.setNameAndId("sheep");
		sheepMob.setHp("2d10+50");

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

		sleepShortWhile();

		assertTrue("sheep and player1 will be engaged in combat", sheepMob
				.getFight().isEngaged(player1Mob));

		// RESCUE
		Command rescue = CommandProvider.getCommand(Rescue.class);
		rescue.execute(player2Mob, "player1");

		sleepShortWhile();

		// Check they are targeting each other, but player 2 now.
		assertEquals("sheep should target player2", player2Mob.getName(),
				sheepMob.getFight().getTarget().getName());

		assertEquals("player 2 should target sheep", sheepMob.getName(),
				player2Mob.getFight().getTarget().getName());

		assertTrue("sheep should target other player", sheepMob.getFight()
				.isEngaged(player2Mob));

		// sleepShortWhile();

		assertFalse("player 1 is no longer engaged with the sheep", player1Mob
				.getFight().isEngaged(sheepMob));

	}

	public void sleepShortWhile() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
