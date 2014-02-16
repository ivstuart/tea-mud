package com.ivstuart.tmud.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ivstuart.tmud.command.combat.Kill;
import com.ivstuart.tmud.command.combat.Rescue;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.PlayerData;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.BaseSkill;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.World;

public class TestRescue {

	@Test
	public void testRescueWhenNotFighting() {

		Mob player1Mob = makeDefaultPlayerMob("player1");
		Mob player2Mob = makeDefaultPlayerMob("player2");

		// Teach rescue to player 2
		Ability rescueAbility = new Ability("rescue", 100);
		player2Mob.getLearned().add(rescueAbility);
		World.add(new BaseSkill("rescue"));

		// TODO have test resource file to load in a mob sheep and mob player
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

	public static Mob makeDefaultPlayerMob(String name) {
		Mob mob = new Mob();
		Player player = new Player();
		player.setMob(mob);
		mob.setNameAndId(name);
		mob.setPlayer(player);
		int[] defaultAttributes = { 10, 10, 10, 10, 10 };
		player.setAttributes(defaultAttributes);

		mob.setId(name);
		mob.setBrief(name);
		mob.setName(name);

		PlayerData data = player.getData();

		mob.setPlayer(player);

		player.setMob(mob);

		player.getData().setAlignment(new Attribute("Alignment", 1000));

		mob.setHeight(6);

		data.setPlayingFor(0);

		data.setTotalXp(0);
		data.setRemort(0);
		data.setPracs(0);

		mob.setLevel(1);

		data.setAge(16 + (int) (Math.random() * 5));

		mob.setHp("100");
		mob.setMv("100");
		mob.setMana(new MobMana(true));

		data.setThirst(500);
		data.setHunger(500);

		return mob;
	}
}
