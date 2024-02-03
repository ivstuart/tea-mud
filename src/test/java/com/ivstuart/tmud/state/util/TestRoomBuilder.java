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
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.places.RoomBuilder;
import com.ivstuart.tmud.state.places.RoomLocation;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestRoomBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() {

        try {
            LaunchMud.loadMudServerProperties();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testRoomBuilder() {



        Room root = new Room(new RoomLocation(0,0,0));

        World.add(root.getRoomLocation(), root);

        RoomBuilder roomBuilder = new RoomBuilder();

        root.setId("Z0-:0:0:0");
        roomBuilder.setId("Z0-:0:0:0");
        roomBuilder.setStartLocation(root.getRoomLocation());

        roomBuilder.setRoomPrefix("Z0-");
        roomBuilder.setPath("3x3");
        roomBuilder.setExecute(null);

        assertEquals("Check number of rooms",12,World.getNumberOfRooms());

        assertFalse("Check exit from first room", World.getPortal(true).getExits().isEmpty());

    }
}
