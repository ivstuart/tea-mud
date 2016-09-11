/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.world;

import com.ivstuart.tmud.utils.MudIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 09/09/2016.
 */
public class Clans {

    private static final Logger LOGGER = LogManager.getLogger();
    private static String fileName = "clan/clan.sav";
    private static Map<Integer, Clan> clans = new HashMap<>();

    public static Collection<Clan> getClans() {
        return clans.values();
    }

    public static Clan getClan(String input) {
        int index = Integer.parseInt(input);

        return clans.get(index);
    }

    public static Clan getClan(Integer clanId) {

        return clans.get(clanId);
    }

    public static void addClan(Clan clan) {
        clans.put(clan.getClanId(), clan);
    }

    public static void init() {
        // Load the data from MudIO

        MudIO mudIO = new MudIO();

        try {
            clans = (Map<Integer, Clan>) mudIO.load(fileName, true);
        } catch (Exception e) {
            LOGGER.warn("Problem loading the post", e);
        }
    }

    public static void shutdown() {
        // Save the data to MudIO.

        MudIO mudIO = new MudIO();

        try {
            mudIO.save(clans, fileName, true);
        } catch (IOException e) {
            LOGGER.error("Problem saving the post", e);
        }

    }
}
