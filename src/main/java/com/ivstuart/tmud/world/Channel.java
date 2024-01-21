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

import java.util.LinkedList;
import java.util.List;

public class Channel {

    private static final int MAX_SIZE = 20;

    private final List<String> good;

    private final List<String> evil;

    /**
     *
     */
    public Channel() {
        super();
        good = new LinkedList<>();
        evil = new LinkedList<>();
    }

    public void add(String message, boolean isGood) {

        if (isGood) {
            add(good, message);
        } else {
            add(evil, message);
        }

    }

    private void add(List<String> list, String message) {
        list.add(message);

        if (list.size() > MAX_SIZE) {
            list.remove(0);
        }
    }

    public String toString(boolean isGood) {
        if (isGood) {
            return toString(good);
        } else {
            return toString(evil);
        }
    }

    private String toString(List<String> list) {
        StringBuilder sb = new StringBuilder();

        for (String lineOfChat : list) {
            sb.append(lineOfChat).append("\n");
        }

        return sb.toString();
    }
}
