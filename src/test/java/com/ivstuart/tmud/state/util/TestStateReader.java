package com.ivstuart.tmud.state.util;

import com.ivstuart.tmud.server.LaunchMud;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TestStateReader {

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

}
