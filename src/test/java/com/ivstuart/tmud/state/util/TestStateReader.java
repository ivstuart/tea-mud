package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.server.LaunchMud;
import com.ivstuart.tmud.state.BaseSkill;
import com.ivstuart.tmud.state.Spell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TestStateReader {

    private static final Logger LOGGER = LogManager.getLogger();

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

        Method method = getMethod(spell,"setLevel", int.class);

        assertEquals("Method check", "setLevel", method.getName());
    }

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

}
