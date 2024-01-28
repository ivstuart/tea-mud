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

import javax.swing.*;
import java.awt.*;

public class JExitsPanel extends JPanel {

    public JExitsPanel(LayoutManager layout) {
        super(layout);
    }

    public JExitsPanel() {
        super();
    }

    public void createButtons() {
        JButton nButton = new JButton("North");
        JButton sButton = new JButton("South");
        JButton eButton = new JButton("East");
        JButton wButton = new JButton("West");
        JButton uButton = new JButton("Up");
        JButton dButton = new JButton("Down");

        JButton customButton = new JButton("Custom");
        JTextField customExitField = new JTextField("custom");
        JTextField customExitFacing = new JTextField("1");

        this.add(nButton);
        this.add(sButton);
        this.add(eButton);
        this.add(wButton);
        this.add(uButton);
        this.add(dButton);

        this.add(customButton);
        this.add(customExitField);
        this.add(customExitFacing);

        nButton.addActionListener(e -> buttonPressed("north", 1));
        sButton.addActionListener(e -> buttonPressed("south", 3));
        eButton.addActionListener(e -> buttonPressed("east", 2));
        wButton.addActionListener(e -> buttonPressed("west", 0));
        uButton.addActionListener(e -> buttonPressed("up", 4));
        dButton.addActionListener(e -> buttonPressed("down", 5));

        int facing = Integer.parseInt(customExitFacing.getText());

        customButton.addActionListener(e -> buttonPressed(customExitField.getText(), facing));
    }

    private void buttonPressed(String direction, int facing) {
        Room selectedRoom = World.getSelectedRoom();
        selectedRoom.toggleExit(direction, facing);

        if (JModePanel.isBidirectional()) {
            GridLocation nextLocation = World.getSelectedRoom().getGridLocation().goFacing(facing);
            Room addjasentRoom = World.getRoom(nextLocation);

            if (addjasentRoom != null) {
                addjasentRoom.toggleExit(Facing.getOpposite(direction), Facing.reverse(facing));

                Exit exit = selectedRoom.getExit(direction);

                if (JModePanel.isEditDoors() && exit != null) {
                    exit.toggleDoor();
                }
            } else {
                if (JModePanel.isEditRooms()) {
                    Room room = new Room(nextLocation);
                    World.addRoom(room);
                }
            }
        }


        this.getRootPane().repaint();
    }

}
