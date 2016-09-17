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

package com.ivstuart.tmud.world;

import com.ivstuart.tmud.server.LaunchMud;
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
    private static String fileName = "clan.sav";
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
            clans = (Map<Integer, Clan>) mudIO.load(getSaveDirectory(), fileName, true);
        } catch (Exception e) {
            LOGGER.warn("Problem loading the post", e);
        }
    }

    public static void shutdown() {
        // Save the data to MudIO.

        MudIO mudIO = new MudIO();

        try {
            mudIO.save(clans, getSaveDirectory(), fileName, true);
        } catch (IOException e) {
            LOGGER.error("Problem saving the post", e);
        }

    }

    public static String getSaveDirectory() {
        return LaunchMud.mudServerProperties.getProperty("clan.save.dir");
    }
}
