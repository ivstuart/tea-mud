package com.ivstuart.tmud.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.state.util.EntityProvider;

public class WorldTime implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private static List<Mob> fighting;

    // TODO Should this not just be handled by the mobs list?
    private static List<DeadMob> deadMobs;

    private static List<Tickable> tickables;

    private static boolean _running = false;
    private static boolean pauseTime = false;

    private static final WorldTime INSTANCE = new WorldTime();
    private final int tickSpeed = 150;
    private int counter = 0;

    public static void addFighting(Mob mob_) {
        // TODO should I have a Set<Fight> for this instead?
        // if (!mob_.getFight().isFighting()) {}
        fighting.add(mob_);
    }

    public static void addTickable(Tickable item_) {
        tickables.add(item_);
    }

    /**
     * @return
     * @Admin
     */
    public static List<Mob> getFighting() {
        return fighting;
    }

    /**
     * Only World and TestHelper are allowed to get an instance of WorldTime
     *
     * @return
     */
    public static WorldTime getInstance() {
        return INSTANCE;
    }

    public static String getTime() {
        // TODO This needs to be updated
        long time = System.currentTimeMillis() / 1000;
        return "Time is : " + time;
    }

    public static void init() {

        fighting = new ArrayList<Mob>();
        tickables = new ArrayList<Tickable>();
        deadMobs = new ArrayList<DeadMob>();
    }

    public static boolean removeItem(Item item_) {
        return tickables.remove(item_);
    }

    public static void scheduleMobForRepopulation(Mob mob_) {
        LOGGER.debug("scheduleMobForRepopulation with id [ " + mob_.getId()
                + " ]");

        DeadMob dead = new DeadMob(mob_.getId(), mob_.getRepopRoomId(), 10);

        deadMobs.add(dead);
    }

    public static void setRunning() {
        _running = true;
    }

    private WorldTime() {
        WorldTime.init();
    }


    public boolean isRunning() {
        return _running;
    }

    public void repopulateMobs() {
        repopulateMobs(false);
    }

    public void repopulateMobs(boolean forceRepop) {
        for (Iterator<DeadMob> iter = deadMobs.iterator(); iter.hasNext(); ) {
            DeadMob deadMob = iter.next();
            if (deadMob.shouldRepopulate() || forceRepop) {

                iter.remove();
                Mob mob = EntityProvider.createMob(deadMob.getID(),
                        deadMob.getRepopRoomID());

                Room repopRoom = World.getRoom(deadMob.getRepopRoomID());

                repopRoom.add(mob);
            }
        }
    }

    /**
     * Resolve combat and remove from combat any that are not fighting
     */
    public void resolveCombat() {

        if (fighting.isEmpty()) {
            return;
        }

        ListIterator<Mob> fightingIter = fighting.listIterator();
        while (fightingIter.hasNext()) {
            Mob mob = fightingIter.next();
            if (mob != null) {
                Fight fight = mob.getFight();

                if (fight.isFighting() || fight.hasFightActions()) {
                    fight.resolveCombat();
                } else {
                    LOGGER.debug("removing from fighting [ " + mob.getName()
                            + " ]");

                    fightingIter.remove();
                }
            }

        }

    }

    @Override
    public void run() {

        setRunning();

        counter = ++counter % tickSpeed;
        if (counter == 1) {

            sendHeartBeat();
            repopulateMobs();
        }

        resolveCombat();

    }

    public void sendHeartBeat() {

        if (pauseTime) {
            return;
        }

        for (Tickable tickable : tickables) {
            tickable.tick();
        }

    }

    public void tickWithCombat() {
        sendHeartBeat();
        repopulateMobs();
        resolveCombat();
    }


    public static boolean togglePauseTime() {
        pauseTime = !pauseTime;
        return pauseTime;
    }
}
