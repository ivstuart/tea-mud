package com.ivstuart.tmud.poc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LaunchWorldBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    private static int maxPathLength = 20;
    private static int maxNumberOfPaths = 20;

    private static boolean isOpen = false;

    public static void main(String[] args) {
        LOGGER.info("Starting world builder");

        Random random = new Random();
        random.setSeed(7757);

        GridLocation gridLocation = new GridLocation(5,5,0);

        Room startingRoom = new Room(gridLocation);

        World.addRoom(startingRoom);

        // Test
        startingRoom.getRoomFlags().setFlag(RoomFlags.DARK);
        startingRoom.getRoomFlags().setFlag(RoomFlags.PEACEFUL);

        LOGGER.info("Starting room:"+startingRoom);

        for (int counter=0;counter<maxNumberOfPaths;counter++) {

            List<GridLocation> locations = getGridLocations(random, gridLocation);

            // Go to a random point on the path and branch in different direction.

            // gridLocation = World.getRandomGridLocation(random);

            gridLocation = getNextGridLocation(locations, random);

            if (gridLocation == null) {
                break;
            }

        }

//        LOGGER.info("Room dump");
//
        // Join open rooms up
        for (Room room : World.getRoomMap().values()) {
            LOGGER.debug("Room:"+room);
            if (!room.isNarrowPassageway()) {
                room.joinNeighbours();
            }

        }

        JFrame frame = new JFrame();
        frame.setTitle("Mud GUI - World Builder and Editor - v0.1");



        //scrollPane.setLayout(new BorderLayout());

        JWorldPanel worldPanel = new JWorldPanel(new BorderLayout());
        worldPanel.setPreferredSize(new Dimension(640,800));
        worldPanel.setVisible(true);
        worldPanel.addClickListener();

        JScrollPane scrollPane = new JScrollPane(worldPanel);
        scrollPane.setPreferredSize(new Dimension(640,400));
        scrollPane.setVisible(true);

        JPanel eastPanel = new JPanel(new BorderLayout());

        JExitsPanel exitsPanel = new JExitsPanel(new GridLayout(9,1));
        exitsPanel.createButtons();
        exitsPanel.setVisible(true);
        exitsPanel.setSize(new Dimension(40,120));

        eastPanel.add(exitsPanel, BorderLayout.NORTH);
        JInputOutputPanel ioPanel = new JInputOutputPanel();
        ioPanel.createButtons();
        eastPanel.add(ioPanel, BorderLayout.SOUTH);


        JBitsPanel jBitsPanel = new JBitsPanel(new GridLayout(10,2));
        jBitsPanel.createComponents();
        eastPanel.add(jBitsPanel, BorderLayout.CENTER);

        frame.add(eastPanel,BorderLayout.EAST);
        frame.add(scrollPane,BorderLayout.CENTER);

        JZoomPanel zoomPanel = new JZoomPanel();
        zoomPanel.createSlider();
        frame.add(zoomPanel,BorderLayout.SOUTH);

        JModePanel modePanel = new JModePanel();
        modePanel.createInterface();
        frame.add(modePanel,BorderLayout.NORTH);


        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        LOGGER.info("Reached this point");

        frame.pack();

    }

    private static GridLocation getNextGridLocation(List<GridLocation> locations, Random random) {

        if(locations.isEmpty()) {
            return null;
        }

        GridLocation gridLocation;
        int index = random.nextInt(locations.size());
        gridLocation = locations.get(index);

        while (!locations.isEmpty() && gridLocation.isOutsideOfZone(World.zone)) {
            index = random.nextInt(locations.size());
            gridLocation = locations.get(index);
            locations.remove(gridLocation);
        }


        return gridLocation;
    }

    private static List<GridLocation> getGridLocations(Random random, GridLocation startLocation) {

        LOGGER.info("Start path:"+startLocation);

        int x = startLocation.getX();
        int y = startLocation.getY();
        int z = 0;

        int facing = random.nextInt(4);

        List<GridLocation> locations = new ArrayList<>(21);

        for (int counter=0;counter<maxPathLength;counter++) {

            GridLocation beforeLocation = new GridLocation(x,y,z);

            switch(facing) {
                case 0: --x; break; // west
                case 1: --y; break; // north
                case 2: ++x; break; // east
                case 3: ++y; break; // south
            }

            GridLocation mapLocation = new GridLocation(x,y,z);

            if(mapLocation.isOutsideOfZone(World.zone)) {
                break;
            }

            locations.add(mapLocation);

            addRoomAndExit(beforeLocation, mapLocation, random, facing);

            if (random.nextInt(100) < 80 ) {
                facing = getFacing(facing, random);;
            }


        }
        return locations;
    }



    private static void addRoomAndExit(GridLocation beforeLocation, GridLocation afterLocation, Random random, int facing) {
        Room beforeRoom = World.getRoom(beforeLocation);
        Room room = World.getRoom(afterLocation);
        if (room != null) {

            // 50% chance of creating a looped path
            if (random.nextInt(100) < 50 ) {
                beforeRoom.addExit(facing,true);
            }
        }
        else {
            room = new Room(afterLocation);

            if (random.nextInt(100) < 10) {
                isOpen = !isOpen;
            }
            room.setNarrowPassageway(isOpen);

            World.addRoom(room);

            beforeRoom.addExit(facing,true);

        }

    }

    private static int getFacing(int facing, Random random) {
        // Update facing
        int newFacing = facing + random.nextInt(3) - 1;

        if (newFacing < 0) {
            newFacing = 3;
        }
        else if (newFacing > 3) {
            newFacing = 0;
        }
        return newFacing;
    }
}

