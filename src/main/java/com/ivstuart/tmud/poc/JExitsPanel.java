package com.ivstuart.tmud.poc;

import javax.swing.*;
import java.awt.*;

public class JExitsPanel extends JPanel {

    public JExitsPanel(LayoutManager layout) {
        super(layout);
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

        nButton.addActionListener(e -> buttonPressed("north",1));
        sButton.addActionListener(e -> buttonPressed("south",3));
        eButton.addActionListener(e -> buttonPressed("east",2));
        wButton.addActionListener(e -> buttonPressed("west",0));
        uButton.addActionListener(e -> buttonPressed("up",4));
        dButton.addActionListener(e -> buttonPressed("down",5));

        int facing = Integer.parseInt(customExitFacing.getText());

        customButton.addActionListener(e -> buttonPressed(customExitField.getText(),facing));
    }

    private void buttonPressed(String direction, int facing) {
        Room selectedRoom =   World.getSelectedRoom();
        selectedRoom.toggleExit(direction, facing);

        if (!JModePanel.isOneWay()) {
            GridLocation nextLocation = World.getSelectedRoom().getGridLocation().goFacing(facing);
            Room addjasentRoom = World.getRoom(nextLocation);

            if(addjasentRoom != null) {
                addjasentRoom.toggleExit(Facing.getOpposite(direction),Facing.reverse(facing));

                Exit exit = selectedRoom.getExit(direction);

                if(JModePanel.isEditDoors() && exit != null) {
                    exit.toggleDoor();
                }
            }
            else {
                if (JModePanel.isEditRooms()) {
                    World.addRoom(new Room(nextLocation));
                }
            }
        }



        this.getRootPane().repaint();
    }

    public JExitsPanel() {
        super();
    }

}
