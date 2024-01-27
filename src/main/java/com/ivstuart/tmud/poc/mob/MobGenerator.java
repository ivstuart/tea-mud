package com.ivstuart.tmud.poc.mob;

import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.poc.GsonIO;
import com.ivstuart.tmud.state.Mob;

import java.io.IOException;
import java.util.*;

public class MobGenerator {

    private static final Map<Integer, List<MobGenerator>> mobs = new HashMap<>();

    private static final String[] rarityNames =
            {"Common", "Uncommon","Rare", "Epic", "Legendary", "Mythical", "Unique"};
    private static final int[] rarityWeighting = {80,10,5,3,1,1,1};

    private final String name;
    private final int level;

    private int xp=0;

    private int item=0;

    private int gold=0;
    private int weighting=0;


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

        mobList.add(new MobGenerator("sheep",5, 10));
        mobList.add(new MobGenerator("cow",7, 10));
        mobList.add(new MobGenerator("chicken",1, 12));
        mobList.add(new MobGenerator("pig",6, 7));
        mobList.add(new MobGenerator("horse",10, 5));
        mobList.add(new MobGenerator("goat",7, 5 ));
        mobList.add(new MobGenerator("lamar",9, 2));
        mobList.add(new MobGenerator("fox",3, 1));
        mobList.add(new MobGenerator("wolf",13, 1));
        mobList.add(new MobGenerator("farmer",30, 1 ));

        mobs.put(0,mobList);
    }

    public static void makeZoneCavern() {

        ArrayList<MobGenerator> list = new ArrayList();

        list.add(new MobGenerator("snake",5, 10));
        list.add(new MobGenerator("bat",7, 10));
        list.add(new MobGenerator("spider",1, 12));
        list.add(new MobGenerator("centipede",6, 7));
        list.add(new MobGenerator("millipede",10, 5));
        list.add(new MobGenerator("blob",7, 5 ));
        list.add(new MobGenerator("fungi",9, 5));
        list.add(new MobGenerator("rat",3, 20));
        list.add(new MobGenerator("skeleton",13, 1));
        list.add(new MobGenerator("witch",30, 1 ));

        mobs.put(1,list);
    }

    public static void main(String[] args) {

        MobGenerator.makeZoneFarm();
        MobGenerator.makeZoneCavern();

        MobGenerator mobGenerator = getRandom(0);

        System.out.println(mobGenerator.getName());
        System.out.println(getRandom(1).getName());

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int weight: rarityWeighting) {
            arrayList.add(weight);
        }

        for (int i=0;i<=10;i++) {
            System.out.println(rarityNames[randomIndexOf(arrayList)]);
        }

        Mob aMob = new Mob();
        aMob.setName(mobGenerator.getName());
        aMob.setHp("117");
        aMob.setMv("80");
        aMob.setHeight(8);
        aMob.setLevel(mobGenerator.getLevel());
        // aMob.setMana("50");
        aMob.setGender("Male");
        aMob.setAlign("1000");

        // aMob.setRoom(room); // Room found in
        aMob.setState("STAND");
        aMob.setXp("90");

        aMob.setAttackType("Punch");
        aMob.setOffensive("100");
        aMob.setDefensive("50");
        aMob.setWimpy(20);

        aMob.setUndead(false);
        aMob.setBehaviour("Patrol");
        aMob.setRaceId(1);
        aMob.setShort("A mob short");
        aMob.setBrief("A mob brief");
        aMob.setLook("A mob is here");
        aMob.setLong("More description here");
        aMob.setDetectInvisible(true);
        aMob.setCopper("100");

        /**
        GsonIO gsonIO = new GsonIO();
        try {
            gsonIO.save(aMob,"/gson/Mob1.gson");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } */

        // TODO set all primitive accessible methods.

    }

    private int getLevel() {
        return level;
    }

    public static MobGenerator getRandom(int zone) {
        List<MobGenerator> list = mobs.get(zone);

        int sum=0;
        for (MobGenerator mobGenerator: list) {
            sum+=mobGenerator.weighting;
        }

        Random random = new Random();
        int result = random.nextInt(sum);

        int counter=0;
        for (MobGenerator mobGenerator: list) {
            result-=mobGenerator.weighting;
            if (result <= 0) {
                return mobGenerator;
            }
            counter++;
        }
        return list.get(list.size()-1);
    }

    public String getName() {
        return name;
    }

    public static int randomIndexOf(List<Integer> integerList) {

        int sum=0;
        for (int i: integerList) {
            sum+=i;
        }

        Random random = new Random();
        int result = random.nextInt(sum);

        int counter=0;
        for (int i: integerList) {
            result-=i;
            if (result <= 0) {
                return counter;
            }
            counter++;
        }
        return integerList.size()-1;
    }
}
