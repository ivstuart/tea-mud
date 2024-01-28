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

package com.ivstuart.tmud.utils;

import com.ivstuart.tmud.server.LaunchMud;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author stuarti
 */
public class MudIO {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final MudIO INSTANCE = new MudIO();

    public static MudIO getInstance() {
        return INSTANCE;
    }

    public Object load(String saveDirectory, String fileName) throws Exception {
        FileInputStream aFileInputStream = new FileInputStream(
                saveDirectory + fileName);

        fileName = fileName.toLowerCase();

        ObjectInputStream aObjectInputStream = new ObjectInputStream(
                new GZIPInputStream(aFileInputStream));

        Object loadedObject = aObjectInputStream.readObject();

        LOGGER.info("Loaded object from file:" + fileName);

        aObjectInputStream.close();

        return loadedObject;
    }

    public Object load(String saveDirectory, String fileName, boolean gzip) throws Exception {

        fileName = fileName.toLowerCase();

        if (gzip) {
            return load(saveDirectory, fileName);
        }

        FileInputStream aFileInputStream = new FileInputStream(
                saveDirectory + fileName);

        ObjectInputStream aObjectInputStream = new ObjectInputStream(
                aFileInputStream);

        Object loadedObject = aObjectInputStream.readObject();

        LOGGER.info("Loaded object from file:" + fileName);

        aObjectInputStream.close();

        return loadedObject;
    }

    public void save(Object saveObject, String dir, String fileName) throws IOException {

        fileName = fileName.toLowerCase();

        ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(
                Files.newOutputStream(Paths.get(dir + fileName))));

        out.writeObject(saveObject);

        LOGGER.info("Saved object to file:" + fileName);

        out.close();
    }

    public void save(Object saveObject, String dir, String fileName, boolean gzip)
            throws IOException {

        fileName = fileName.toLowerCase();

        if (gzip) {
            save(saveObject, dir, fileName);
            return;
        }

        ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(dir + fileName)));

        out.writeObject(saveObject);

        LOGGER.info("Saved object to file:" + fileName);

        out.close();
    }

    public String getSaveDirectory() {
        return LaunchMud.mudServerProperties.getProperty("player.save.dir");
    }

}
