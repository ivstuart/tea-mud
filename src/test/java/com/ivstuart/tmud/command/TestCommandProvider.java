package com.ivstuart.tmud.command;

import junit.framework.TestCase;
import org.junit.Test;

public class TestCommandProvider extends TestCase {

    @Test
    public void testLastTokenBoundary() {

        String lastToken = CommandProvider.getLowerCaseLastToken(
                "first.second.third.", ".");

        assertEquals("Boundary check of last token", "", lastToken);
    }

    @Test
    public void testLastTokenBoundaryLongDelim() {

        String lastToken = CommandProvider.getLowerCaseLastToken(
                "first.second.", "second.");

        assertEquals("Boundary check of last token", "", lastToken);
    }

    @Test
    public void testLastTokenLongDelim() {

        String lastToken = CommandProvider.getLowerCaseLastToken(
                "first.second.third", "second.");

        assertEquals("Long delim check of last token", "third", lastToken);
    }

    @Test
    public void testLastTokenNoDelimInMessage() {

        String lastToken = CommandProvider.getLowerCaseLastToken("firstsecond",
                ".");

        assertEquals("No delim found check of last token", "firstsecond",
                lastToken);
    }

    @Test
    public void testLastTokenNormal() {

        String lastToken = CommandProvider.getLowerCaseLastToken(
                "first.second.third", ".");

        assertEquals("Normal check of last token", "third", lastToken);
    }

    @Test
    public void testLastTokenNullDelim() {

        String lastToken = CommandProvider.getLowerCaseLastToken("first", null);

        assertEquals("Null delim check of last token", "first", lastToken);

    }

    @Test
    public void testLastTokenNullMessage() {

        String lastToken = CommandProvider.getLowerCaseLastToken(null, ".");

        assertNull("Null message check of last token", lastToken);

    }

    @Test
    public void testLastTokenNullNull() {

        String lastToken = CommandProvider.getLowerCaseLastToken(null, null);

        assertNull("Null null check of last token", lastToken);

    }

    @Test
    public void testLastTokenSimple() {

        String lastToken = CommandProvider.getLowerCaseLastToken(
                "first.second", ".");

        assertEquals("Simple check of last token", "second", lastToken);
    }

    @Test
    public void testReloadInValidCommand() {

        boolean isReloaded = CommandProvider
                .reloadCommand("command.move.Invalid");

        assertFalse("Check invalid reload of command", isReloaded);
    }

    @Test
    public void testReloadValidCommand() {

        boolean isReloaded = CommandProvider.reloadCommand("command.move.Down");

        assertTrue("Check valid reload of command", isReloaded);
    }

}
