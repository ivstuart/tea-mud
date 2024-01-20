package com.ivstuart.tmub.server;

import com.ivstuart.tmud.common.Colour;
import com.ivstuart.tmud.server.Output;
import junit.framework.TestCase;
import org.junit.Test;

public class OutputTest extends TestCase {

    @Test
    public void testRemovingAnsiEncoding() {
        String message = "The sky is mostly " + Colour.BLUE + "blue and can be " + Colour.RED + "red " + Colour.NONE + "in places at sunset";

        String output = Output.removeAnsi(message);

        assertEquals("Check only text displayed", "The sky is mostly blue and can be red in places at sunset", output);
    }

    @Test
    public void testAnsiPresent() {
        String message = "The sky is mostly " + Colour.BLUE + "blue and can be " + Colour.RED + "red " + Colour.NONE + "in places at sunset";

        String output = Output.getString(message, true);

        assertEquals("Check only text displayed", message, output);
    }

}
