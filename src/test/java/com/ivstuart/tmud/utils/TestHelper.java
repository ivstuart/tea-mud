/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.utils;

import com.ivstuart.tmud.client.LaunchClient;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.PlayerData;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.WorldTime;

import java.io.IOException;

public class TestHelper {
	
	private Thread serverThread = null;

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
	
	public void combatTick() {
		WorldTime.getInstance().resolveCombat();
	}
	
	public void tick() {
		WorldTime.getInstance().tickWithCombat();
	}
	
	public void heartBeat() {
		WorldTime.getInstance().sendHeartBeat();
	}
	
	public void repop() {
		WorldTime.getInstance().repopulateMobs();
	}
	
	public LaunchMudClient startClient() {
		LaunchMudClient client = new LaunchMudClient();
		Thread clientThread = new Thread(client);
		clientThread.start();

		return client;
	}
	
	public void startServer() {
		Thread serverThread = new Thread(new LaunchMudRunnable());
		serverThread.start();
		try {
			Thread.sleep(200); // Give server time to come up.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean stopServer() {
		return LaunchMud.stop();
    }

    class LaunchMudRunnable implements Runnable {

        boolean isRunning = true;

        @Override
        public void run() {

            LaunchMud.main(new String[0]);

            try {
                Thread.sleep(15 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // 15 minutes no shutdown coded yet.

            isRunning = false;
        }

	}

    class LaunchMudClient implements Runnable {

		boolean isRunning = true;

		LaunchClient client = null;

		@Override
		public void run() {

			try {
				client = LaunchClient.init();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(15 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // 15 minutes no shutdown coded yet.

			isRunning = false;

		}

		public void send(String userInput) throws IOException {

			client.send(userInput);
		}
	}
}
