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

import com.google.gson.reflect.TypeToken;
import com.ivstuart.tmud.person.Note;
import com.ivstuart.tmud.utils.GsonIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ivan on 09/09/2016.
 */
public class Boards {

    private static final Logger LOGGER = LogManager.getLogger();

    private static String fileName = "/boards/messages";

    private static List<Note> notes;

    public static void postNote(Note note) {
        notes.add(note);

        GsonIO io = new GsonIO();

        try {
            io.save(notes, fileName);
        } catch (IOException e) {
            LOGGER.error("IO problem ", e);
        }
    }

    public static void init() {
        GsonIO io = new GsonIO();

        try {
            Type collectionType = new TypeToken<Collection<Note>>() {
            }.getType();
            notes = (List<Note>) io.load(fileName, collectionType);
        } catch (IOException e) {
            notes = new ArrayList<>();
            LOGGER.error("IO problem ", e);
        }
    }

    public static Note getNote(int index) {

        if (notes.size() <= index) {
            return null;
        }

        return notes.get(index);
    }
}
