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

    public static void setGridLocation(Room room) {

        xTextField.setText("" + room.getGridLocation().getX());
        yTextField.setText("" + room.getGridLocation().getY());
        zTextField.setText("" + room.getGridLocation().getZ());

        zoneTextField.setText(""+room.getZoneId());

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
                gsonIO.save(World.getRoomMap(), fileName);
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

            World.getRoomMap().clear();

            GsonIO gsonIO = new GsonIO();

            Object loadedObject;

            try {
                loadedObject = gsonIO.load(fileName, World.getRoomMap().getClass());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Map<GridLocation, Room> loadedMap;

            if (loadedObject instanceof Map) {
                loadedMap = (Map<GridLocation, Room>) loadedObject;
            } else {
                LOGGER.error("Problem loading not a Map");
                return;
            }


            Gson gson = new Gson();

            for (Object value : loadedMap.values()) {
                LOGGER.info("Debugging value: " + value);

                Room room = gson.fromJson(value.toString(), Room.class);

                World.addRoom(room);
            }

            LOGGER.info("Debugging World: " + World.getRoomMap());

        }

    }


    private void clearWorld() {

        Map<GridLocation, Room> roomMap = World.getRoomMap();
        roomMap.clear();
        JRootPane rootPane = this.getRootPane();
        rootPane.repaint();
    }
}
