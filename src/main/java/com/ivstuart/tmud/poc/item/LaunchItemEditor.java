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

import com.ivstuart.tmud.state.items.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class LaunchItemEditor {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final  JItemFieldPanel itemFieldPanel = new JItemFieldPanel();

    private static final JFrame frame = new JFrame();

    public static void main(String[] args) {

        frame.setTitle("Mud GUI - Item Builder and Editor - v0.1");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(640, 800));

        LOGGER.info("Reached this point");

        JItemControlPanel itemControlPanel = new JItemControlPanel();
        itemControlPanel.createUI();

        itemFieldPanel.setLayout(new GridLayout(30,4));

        String itemName = JItemControlPanel.getDropDownValue();

        Class<?> itemClass = getItemClass(itemName);

        itemFieldPanel.createFields(itemClass);

        JScrollPane scrollPane = new JScrollPane(itemFieldPanel);

        frame.add(itemControlPanel, BorderLayout.NORTH);
        frame.add(scrollPane,BorderLayout.CENTER);

        frame.pack();
    }

    private static Class<?> getItemClass(String itemName) {
        try {
            return Class.forName("com.ivstuart.tmud.state.items." + itemName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static JItemFieldPanel getItemFieldPanel() {
        return itemFieldPanel;
    }

    public static void repaint() {
        frame.repaint();
        frame.pack();
    }
}
