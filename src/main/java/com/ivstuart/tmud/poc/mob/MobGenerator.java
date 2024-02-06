/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.poc.mob;

import com.ivstuart.tmud.state.mobs.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.ivstuart.tmud.poc.mob.RarityEnum.COMMON;

public class MobGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Integer, List<MobGenerator>> mobs = new HashMap<>();

    private final String name;
    private final int level;

    private final RarityEnum rarity = COMMON;

    private int xp = 0;

    private int item = 0;

    private int gold = 0;
    private int weighting = 0;


    public MobGenerator(String name, int level, int weighting) {
        this.name = name;
        this.level = level;
        this.weighting = weighting;
    }

    public MobGenerator(String name, int level, int xp, int item, int gold) {
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.item = item;
        this.gold = gold;
    }

    public static void makeZoneFarm() {

        ArrayList<MobGenerator> mobList = new ArrayList();

        mobList.add(new MobGenerator("sheep", 5, 10));
        mobList.add(new MobGenerator("cow", 7, 10));
        mobList.add(new MobGenerator("chicken", 1, 12));
        mobList.add(new MobGenerator("pig", 6, 7));
        mobList.add(new MobGenerator("horse", 10, 5));
        mobList.add(new MobGenerator("goat", 7, 5));
        mobList.add(new MobGenerator("lamar", 9, 2));
        mobList.add(new MobGenerator("fox", 3, 1));
        mobList.add(new MobGenerator("wolf", 13, 1));
        mobList.add(new MobGenerator("farmer", 30, 1));
        mobList.add(new MobGenerator("mouse", 1, 3));
        mobs.put(0, mobList);
    }

    public static void makeZoneCavern() {

        ArrayList<MobGenerator> list = new ArrayList();

        list.add(new MobGenerator("snake", 5, 10));
        list.add(new MobGenerator("bat", 7, 10));
        list.add(new MobGenerator("spider", 1, 12));
        list.add(new MobGenerator("centipede", 6, 7));
        list.add(new MobGenerator("millipede", 10, 5));
        list.add(new MobGenerator("blob", 7, 5));
        list.add(new MobGenerator("fungi", 9, 5));
        list.add(new MobGenerator("rat", 3, 20));
        list.add(new MobGenerator("skeleton", 13, 1));
        list.add(new MobGenerator("witch", 30, 1));

        mobs.put(1, list);
    }

    public static void makeZoneAviary() {

        ArrayList<MobGenerator> list = new ArrayList();
        list.add(new MobGenerator("hummingbird", 5, 1));
        list.add(new MobGenerator("owl", 7, 1));
        list.add(new MobGenerator("finch", 1, 1));
        list.add(new MobGenerator("sparrow", 6, 1));
        list.add(new MobGenerator("crane", 10, 1));
        list.add(new MobGenerator("cuckoo", 7, 1));
        list.add(new MobGenerator("kingfisher", 9, 1));
        list.add(new MobGenerator("toucan", 3, 1));
        list.add(new MobGenerator("bird of paradise", 13, 1));
        list.add(new MobGenerator("heron", 8, 1));
        list.add(new MobGenerator("swallow", 8, 1));
        list.add(new MobGenerator("stork", 8, 1));
        list.add(new MobGenerator("albatross", 8, 1));
        list.add(new MobGenerator("woodpecker", 8, 1));
        list.add(new MobGenerator("cormorant", 8, 1));
        list.add(new MobGenerator("grouse", 8, 1));
        list.add(new MobGenerator("vulture", 8, 1));
        list.add(new MobGenerator("swan", 12, 1));
        mobs.put(2, list);
    }

    public static void makeZoneDesert() {

        ArrayList<MobGenerator> list = new ArrayList();
        list.add(new MobGenerator("lion", 25, 1));
        list.add(new MobGenerator("tiger", 17, 1));
        list.add(new MobGenerator("elephant", 30, 1));
        list.add(new MobGenerator("lizard", 30, 1));
        list.add(new MobGenerator("camel", 30, 1));
        list.add(new MobGenerator("scorpion", 30, 1));
        mobs.put(3, list);
    }

    public static void makeZoneForest() {
        ArrayList<MobGenerator> list = new ArrayList();
        list.add(new MobGenerator("ape", 25, 1));
        list.add(new MobGenerator("deer", 17, 1));
        list.add(new MobGenerator("bear", 30, 1));
        list.add(new MobGenerator("wolf", 30, 1));
        list.add(new MobGenerator("boar", 30, 1));
        list.add(new MobGenerator("cougar", 30, 1));
        list.add(new MobGenerator("elk", 30, 1));
        list.add(new MobGenerator("fox", 30, 1));
        list.add(new MobGenerator("hare", 30, 1));
        list.add(new MobGenerator("rabbit", 30, 1));
        list.add(new MobGenerator("squirrel", 30, 1));
        list.add(new MobGenerator("gorilla", 30, 1));
        list.add(new MobGenerator("chimpanzee", 30, 1));
        mobs.put(4, list);
    }

    public static void makeZoneMountain() {
        ArrayList<MobGenerator> list = new ArrayList();
        list.add(new MobGenerator("mountain goat", 25, 1));
        list.add(new MobGenerator("mountain gorilla", 17, 1));
        list.add(new MobGenerator("ibex", 30, 1));
        list.add(new MobGenerator("snow leopard", 30, 1));
        list.add(new MobGenerator("lynx", 30, 1));
        list.add(new MobGenerator("coyote", 30, 1));
        list.add(new MobGenerator("viper", 30, 1));
        list.add(new MobGenerator("golden eagle", 30, 1));
        mobs.put(5, list);
    }

    public static void makeZonePyramid() {
        ArrayList<MobGenerator> list = new ArrayList();
        list.add(new MobGenerator("snake", 25, 1));
        list.add(new MobGenerator("mummy", 17, 1));
        list.add(new MobGenerator("skeleton", 30, 1));
        list.add(new MobGenerator("pharaoh", 30, 1));
        list.add(new MobGenerator("scarab", 30, 1));
        list.add(new MobGenerator("scorpion", 30, 1));
        list.add(new MobGenerator("asp", 30, 1));
        list.add(new MobGenerator("ghost", 30, 1));
        mobs.put(6, list);
    }

    public static void makeZoneSwamp() {
        ArrayList<MobGenerator> list = new ArrayList();
        list.add(new MobGenerator("crocodile", 25, 1));
        list.add(new MobGenerator("alligator", 17, 1));
        list.add(new MobGenerator("eel", 30, 1));
        list.add(new MobGenerator("hippopotamus", 30, 1));
        list.add(new MobGenerator("water snake", 30, 1));
        list.add(new MobGenerator("marsh light", 30, 1));
        list.add(new MobGenerator("asp", 30, 1));
        list.add(new MobGenerator("ghost", 30, 1));
        mobs.put(7, list);
    }

    public static void load() {
        MobGenerator.makeZoneFarm();
        MobGenerator.makeZoneCavern();
        MobGenerator.makeZoneAviary();
        MobGenerator.makeZoneDesert();
        MobGenerator.makeZonePyramid();
        MobGenerator.makeZoneMountain();
        MobGenerator.makeZoneForest();
        MobGenerator.makeZoneSwamp();
    }

    public static void main(String[] args) {

        load();

        MobGenerator mobGenerator = getRandom(0);

        ArrayList<Integer> arrayList = new ArrayList<>();

        Mob mob = createMob(mobGenerator, RarityEnum.getRandom());

        LOGGER.info("Mob is:"+mob);



        /**
         GsonIO gsonIO = new GsonIO();
         try {
         gsonIO.save(aMob,"/gson/Mob1.gson");
         } catch (IOException e) {
         throw new RuntimeException(e);
         } */

        // TODO set all primitive accessible methods.

    }

    public static Mob createMob(MobGenerator mobGenerator, RarityEnum rarity) {
        Mob aMob = new Mob();
        aMob.setName(mobGenerator.getName());
        aMob.setAlias(aMob.getName());

        // 10 + 5 * level * level TODO balance values and modify by rarity
        int hp = 10 + (5 * mobGenerator.getLevel() * mobGenerator.getLevel());
        aMob.setHp(""+hp);
        aMob.setMv(""+hp);
        aMob.getMobBodyStats().setHeight(180);
        aMob.setLevel(mobGenerator.getLevel());
        // aMob.setMana("50");
        aMob.getMobBodyStats().setGender("Male");
        aMob.getMobNpc().setAlign(1000);

        // aMob.setRoom(room); // Room found in
        aMob.setState("STAND");
        aMob.setXp(hp);

        aMob.setAttackType("Punch");
        aMob.getMobCombat().setOffensive(100 + 10*mobGenerator.getLevel());
        aMob.getMobCombat().setDefensive(50 +5*mobGenerator.getLevel());
        aMob.getMobCombat().setWimpy(0);

        aMob.getMobBodyStats().setUndead(false);
        //aMob.getMobNpc().setBehaviour("Patrol");
        //aMob.getMobBodyStats().setRaceId(1);
        aMob.setShort("A mob short");
        aMob.setBrief("A "+aMob.getName()+" is here - brief");
        aMob.setLook("A "+aMob.getName()+" is here - look");
        aMob.setVerbose("More description here");
        // aMob.setDetectInvisible(true);
        // aMob.setCopper(100);

        //aMob.setFlag("Memory");
        //aMob.setFlag(MobEnum.AGGRO.name());
        //aMob.setFlag("Foo");
        return aMob;
    }

    public static MobGenerator getRandom(int zone) {
        List<MobGenerator> list = mobs.get(zone);

        int sum = 0;
        for (MobGenerator mobGenerator : list) {
            sum += mobGenerator.weighting;
        }

        Random random = new Random();
        int result = random.nextInt(sum);

        int counter = 0;
        for (MobGenerator mobGenerator : list) {
            result -= mobGenerator.weighting;
            if (result <= 0) {
                return mobGenerator;
            }
            counter++;
        }
        return list.get(list.size() - 1);
    }

    public static int randomIndexOf(List<Integer> integerList) {

        int sum = 0;
        for (int i : integerList) {
            sum += i;
        }

        Random random = new Random();
        int result = random.nextInt(sum);

        int counter = 0;
        for (int i : integerList) {
            result -= i;
            if (result <= 0) {
                return counter;
            }
            counter++;
        }
        return integerList.size() - 1;
    }

    private int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }
}
