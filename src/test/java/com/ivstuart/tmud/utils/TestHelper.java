/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.utils;

import com.ivstuart.tmud.client.LaunchClient;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.PlayerData;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.world.World;
import com.ivstuart.tmud.world.WorldTime;

import java.io.IOException;

public class TestHelper {

    private Thread serverThread = null;

    public static Mob makeDefaultPlayerMob(String name) {
        Mob mob = new Mob();
        Player player = new Player();
        player.setMob(mob);
        mob.setNameAndId(name);
        mob.setAlias(name);
        mob.setPlayer(player);
        int[] defaultAttributes = {10, 10, 10, 10, 10};
        player.setAttributes(defaultAttributes);

        mob.setId(name);
        mob.setBrief(name);
        mob.setName(name);

        PlayerData data = player.getData();

        data.setLearns(100);
        data.setPracs(100);

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

        mob.setWimpy(10);

        return mob;
    }

    public static Room makeRoomGrid() {
        Room root = new Room();
        root.setId("Z0-:0:0:0");

        World.add(root);

        RoomBuilder roomBuilder = new RoomBuilder();
        roomBuilder.setId("Z0-:0:0:0");
        roomBuilder.setRoomPrefix("Z0-");
        roomBuilder.setPath("3x3");
        roomBuilder.setExecute(null);


        return root;
    }

    /**
     * id: dagger-001
     * alias: dagger
     * short: dagger
     * look: dagger
     * skill: piercing
     * type: weapon SHARP
     * damage: 3d2+1
     * wear: PRIMARY SECONDARY
     * weight: 1
     * cost: 2
     * rent: 1
     * affects:
     *
     * @param player1Mob
     */
    public static void equipDagger(Mob player1Mob) {

        Weapon dagger = new Weapon();
        dagger.setId("dagger-01");
        dagger.setDamage("20");
        dagger.setWear("PRIMARY SECONDARY");
        dagger.setType("SHARP");
        dagger.setSkill("piercing");

        player1Mob.getEquipment().add(dagger);

    }

    public static Teacher makeTeacherProp(String name) {
        Teacher teacher = new Teacher();
        teacher.setId(name);
        teacher.setAlias(name);
        teacher.setAbility("magic missile");

        return teacher;

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
