package com.ivstuart.tmud.utils;

import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

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
			e.printStackTrace();
		}
	}

	@Test
	public void testLoadingPlayer() throws IOException {

		GsonIO saveGson = new GsonIO();
		Player player = (Player) saveGson.loadPlayer("testplayer");

		assertEquals("check player name", "player1", player.getName());

	}

}
