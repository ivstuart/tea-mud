/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
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
