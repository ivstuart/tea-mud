package com.ivstuart.tmud.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.World;

public class SaveGsonTest {

	TestHelper help = new TestHelper();

	@Test
	public void testSavingPlayer() {
		GsonIO saveGson = new GsonIO();

		Mob playerMob = TestHelper.makeDefaultPlayerMob("player1");
		Player player = playerMob.getPlayer();

		try {
			saveGson.save(player, "testplayer");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testLoadingPlayer() throws IOException {

		GsonIO saveGson = new GsonIO();
		Player player = (Player) saveGson.loadPlayer("testplayer");

		assertEquals("check player name", "player1", player.getName());

	}

	// TODO this failed due with stack overflow - either cyclic references or another issue. Transient fields need to be excluded.
//	@Test
//	public void testSavingRoom() {
//		help.startServer();
//
//		GsonIO saveGson = new GsonIO();
//
//		try {
//			saveGson.save(World.getRoom("R-001-001"), "testroom");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		help.stopServer();
//	}
}
