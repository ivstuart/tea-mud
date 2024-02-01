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

package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.skills.BaseSkill;
import com.ivstuart.tmud.state.skills.Spell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TestStateReader {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Method getMethod(Object object, String name, Class<?> aClass) {
        Method method = null;
        try {
            method = object.getClass().getMethod(name, aClass);
            // method = object.getClass().getDeclaredMethod(name, aClass);
        } catch (NoSuchMethodException e) {
            LOGGER.warn("No such method called!", e);
        }
        return method;
    }

    @Test
    public void testNotNull() {
        StateReader reader = StateReader.getInstance();

        assertNotNull(reader);
    }

    @Test
    public void testClassPrefix() {
        try {
            LaunchMud.loadMudServerProperties();
        } catch (URISyntaxException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertEquals("Class prefix check", "com.ivstuart.tmud.", StateReader.getInstance().getClassPrefix());

    }

    @Test
    public void testArgumentsFound() {
        String line = "somecommand: parameters";

        String args = StateReader.getInstance().getArgs(line, line.indexOf(":"));

        assertEquals("arguments", "parameters", args);
    }

    @Test
    public void testSpellMethodSetLevel() {

        Spell spell = new Spell();
        BaseSkill baseSkill = new BaseSkill();

        Method method = getMethod(spell, "setLevel", int.class);

        assertEquals("Method check", "setLevel", method.getName());
    }

}
