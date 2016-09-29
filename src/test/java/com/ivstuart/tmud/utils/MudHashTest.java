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

package com.ivstuart.tmud.utils;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Ivan on 29/09/2016.
 */
public class MudHashTest {

    @Test
    public void testMudHashAdd() {
        MudHash<String> mudHash = new MudHash<>();

        mudHash.add("test", "Test");

        assertEquals("Test", mudHash.get("t"));
        assertEquals("Test", mudHash.get("te"));
        assertEquals("Test", mudHash.get("tes"));
        assertEquals("Test", mudHash.get("test"));
    }

    @Test
    public void testMudHashDefault() {
        MudHash<String> mudHash = new MudHash<>();

        mudHash.setDefault("Default");

        assertEquals("Default", mudHash.get("t"));
        assertEquals("Default", mudHash.get("te"));
        assertEquals("Default", mudHash.get("tes"));
        assertEquals("Default", mudHash.get("test"));
    }

    @Test
    public void testMudHashClear() {
        MudHash<String> mudHash = new MudHash<>();

        mudHash.add("test", "Test");

        assertEquals("Test", mudHash.get("t"));
        assertEquals("Test", mudHash.get("te"));
        assertEquals("Test", mudHash.get("tes"));
        assertEquals("Test", mudHash.get("test"));

        mudHash.clear();

        assertEquals(0, mudHash.values().size());
    }

    @Test
    public void testMudHashRemove() {
        MudHash<String> mudHash = new MudHash<>();

        mudHash.add("test", "Test");

        assertEquals("Test", mudHash.get("t"));
        assertEquals("Test", mudHash.get("te"));
        assertEquals("Test", mudHash.get("tes"));
        assertEquals("Test", mudHash.get("test"));

        mudHash.remove("test");

        assertEquals(0, mudHash.values().size());
    }

    @Test
    public void testMudHashReplace() {
        MudHash<String> mudHash = new MudHash<>();

        mudHash.add("test", "Test");
        mudHash.replace("test", "Test2");

        assertEquals("Test", mudHash.get("t"));
        assertEquals("Test2", mudHash.get("te"));
        assertEquals("Test2", mudHash.get("tes"));
        assertEquals("Test2", mudHash.get("test"));

    }
}
