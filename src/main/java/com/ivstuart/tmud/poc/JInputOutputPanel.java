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

package com.ivstuart.tmud.poc;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JInputOutputPanel extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final JTextField xTextField = new JTextField();
    private static final JTextField yTextField = new JTextField();
    private static final JTextField zTextField = new JTextField();

    private static final JTextField zoneTextField = new JTextField();

    public static void setGridLocation(Place room) {

        xTextField.setText("" + room.getGridLocation().getX());
        yTextField.setText("" + room.getGridLocation().getY());
        zTextField.setText("" + room.getGridLocation().getZ());

        zoneTextField.setText("" + room.getZoneId());

    }


    public void createButtons() {

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        JButton cButton = new JButton("Clear");
        JButton nButton = new JButton("Load");
        JButton sButton = new JButton("Save");

        JLabel xLabel = new JLabel("Grid X:");
        JLabel yLabel = new JLabel("Grid Y:");
        JLabel zLabel = new JLabel("Grid Z:");

        JLabel zoneLabel = new JLabel("Zone:");

        buttonPanel.add(cButton);
        buttonPanel.add(nButton);
        buttonPanel.add(sButton);

        this.add(buttonPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(4, 2));


        gridPanel.add(xLabel);
        gridPanel.add(xTextField);
        gridPanel.add(yLabel);
        gridPanel.add(yTextField);
        gridPanel.add(zLabel);
        gridPanel.add(zTextField);

        gridPanel.add(zoneLabel);
        gridPanel.add(zoneTextField);


        this.add(gridPanel, BorderLayout.CENTER);

        cButton.addActionListener(e -> clearWorld());

        nButton.addActionListener(e -> loadWorld());

        sButton.addActionListener(e -> saveWorld());
    }

    private void saveWorld() {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Zone files only", "zone");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Store zone file");
        fileChooser.setApproveButtonText("Save");
        fileChooser.setCurrentDirectory(new File("./src/main/resources/saved/"));
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            String fileName = fileChooser.getSelectedFile().getName();

            LOGGER.info("You choose to save this file: " + fileName);

            GsonIO gsonIO = new GsonIO();
            try {
                gsonIO.save(WorldMap.getRoomMap(), fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void loadWorld() {

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Zone files only", "zone");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Load zone file");
        fileChooser.setApproveButtonText("Load");
        fileChooser.setCurrentDirectory(new File("./src/main/resources/saved/"));
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getName();
            LOGGER.info("You choose to load this file: " + fileName);

            WorldMap.getRoomMap().clear();

            GsonIO gsonIO = new GsonIO();

            Object loadedObject;

            try {
                loadedObject = gsonIO.load(fileName, WorldMap.getRoomMap().getClass());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Map<GridLocation, Place> loadedMap;

            if (loadedObject instanceof Map) {
                loadedMap = (Map<GridLocation, Place>) loadedObject;
            } else {
                LOGGER.error("Problem loading not a Map");
                return;
            }


            Gson gson = new Gson();

            for (Object value : loadedMap.values()) {
                LOGGER.info("Debugging value: " + value);

                Place room = gson.fromJson(value.toString(), Place.class);

                WorldMap.addRoom(room);
            }

            LOGGER.info("Debugging World: " + WorldMap.getRoomMap());

        }

    }


    private void clearWorld() {

        Map<GridLocation, Place> roomMap = WorldMap.getRoomMap();
        roomMap.clear();
        JRootPane rootPane = this.getRootPane();
        rootPane.repaint();
    }
}
