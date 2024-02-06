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

package com.ivstuart.tmud.poc.item;
import com.ivstuart.tmud.poc.GsonIO;
import com.ivstuart.tmud.state.items.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class JItemControlPanel extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final JSpinner spinner = new JSpinner();
    private static final JComboBox<String> selector = new JComboBox<>();

    public static int getRecordId() {
        return Integer.valueOf(spinner.getValue().toString());
    }

    public void createUI() {

        selector.addItem("Item"); // Add subclasses
        selector.addItem("Mob"); // Add subclasses.
        this.add(selector);

        spinner.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        spinner.setPreferredSize(new Dimension(40, 26));
        spinner.setModel(new SpinnerNumberModel(0, 0, 999, 1));
        this.add(spinner);

        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton clear = new JButton("Clear");

        JButton load = new JButton("Load");
        JButton save = new JButton("Save");

        this.add(add);
        this.add(remove);
        this.add(clear);
        this.add(load);
        this.add(save);

        selector.addActionListener(e -> dropDownChoice());

        spinner.addChangeListener(e -> recordChanged());

        add.addActionListener(e -> addButton());

        remove.addActionListener(e -> removeButton());

        clear.addActionListener(e -> clearButton());

        save.addActionListener(e -> saveButton());

        load.addActionListener(e -> loadButton());
    }

    private void loadButton() {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Gson files only", "gson");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Load item map");
        fileChooser.setApproveButtonText("Load");
        fileChooser.setCurrentDirectory(new File("./src/main/resources/saved/gson/"));
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getName();
            LOGGER.info("You choose to load this file: " + fileName);

            JItemFieldPanel.getMap().clear();

            GsonIO gsonIO = new GsonIO();

            Object loadedObject;

            try {
                loadedObject = gsonIO.load(fileName, JItemFieldPanel.getMap().getClass());
            } catch (IOException e) {
                LOGGER.error("Problem loading:",e);
                throw new RuntimeException(e);
            }

            Map<Integer, Item> loadedMap = (Map<Integer, Item>) loadedObject;

            LOGGER.info("Loading in object:"+loadedMap);

            JItemFieldPanel.setMap(loadedMap);

        }

    }

    private void saveButton() {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Gson files only", "gson");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Store item map");
        fileChooser.setApproveButtonText("Save");
        fileChooser.setCurrentDirectory(new File("./src/main/resources/saved/gson/"));
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            String fileName = fileChooser.getSelectedFile().getName();

            LOGGER.info("You choose to save this file: " + fileName);

            Map map = JItemFieldPanel.getMap();

            GsonIO gsonIO = new GsonIO();
            try {
                gsonIO.save(map, fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }

    private void dropDownChoice() {
        LOGGER.debug("Drop down selected:" + selector.getSelectedItem());
        //JItemFieldPanel.clear();
        //JItemFieldPanel.createUI();
    }

    private void recordChanged() {
        try {
            JItemFieldPanel.recordUpdated();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeButton() {
        JItemFieldPanel.removePressed();
    }

    private void clearButton() {
        JItemFieldPanel.clearPressed();
    }

    private void addButton() {
        LOGGER.debug("Add pressed");

        try {
            JItemFieldPanel.addPressed();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
