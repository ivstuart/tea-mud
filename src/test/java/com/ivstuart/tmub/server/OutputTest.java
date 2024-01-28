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
