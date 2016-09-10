/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.world;

import com.ivstuart.tmud.state.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 10/09/2016.
 */
public class PostalSystem {


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
}
