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

package com.ivstuart.tmud.world;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.utils.MudIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 10/09/2016.
 */
public class PostalSystem {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String fileName = "post.sav";
    private static Map<String, List<Item>> post = new HashMap<>();

    public static void post(Item item, String player) {

        List<Item> items = post.get(player);

        if (items == null) {
            items = new ArrayList<>();
            post.put(player, items);
        }

        items.add(item);

    }

    public static Item collect(String player) {
        List<Item> items = post.get(player);
        if (items == null || items.isEmpty()) {
            return null;
        }
        return items.remove(0);
    }

    public static void init() {
        // Load the data from MudIO

        MudIO mudIO = new MudIO();

        try {
            post = (Map<String, List<Item>>) mudIO.load(getSaveDirectory(), fileName, true);
        } catch (Exception e) {
            LOGGER.warn("Problem loading the post", e);
        }
    }

    public static void shutdown() {
        // Save the data to MudIO.

        MudIO mudIO = new MudIO();

        try {
            mudIO.save(post, getSaveDirectory(), fileName, true);
        } catch (IOException e) {
            LOGGER.error("Problem saving the post", e);
        }

    }

    public static String getSaveDirectory() {
        return LaunchMud.mudServerProperties.getProperty("post.save.dir");
    }
}
