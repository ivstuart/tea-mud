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
        // JButton customButton = new JButton("Custom");

        this.add(nButton);
        this.add(sButton);
        this.add(eButton);
        this.add(wButton);
        this.add(uButton);
        this.add(dButton);

        nButton.addActionListener(e -> buttonPressed("north",1));
        sButton.addActionListener(e -> buttonPressed("south",3));
        eButton.addActionListener(e -> buttonPressed("east",2));
        wButton.addActionListener(e -> buttonPressed("west",0));
        uButton.addActionListener(e -> buttonPressed("up",4));
        dButton.addActionListener(e -> buttonPressed("down",5));
    }

    private void buttonPressed(String direction, int facing) {
        World.getSelectedRoom().toggleExit(direction, facing);

        if (!JModePanel.isOneWay()) {
            GridLocation nextLocation = World.getSelectedRoom().getGridLocation().goFacing(facing);
            Room roomNeightbour = World.getRoom(nextLocation);

            if(roomNeightbour != null) {
                roomNeightbour.toggleExit(Exit.getOppositeDirection(direction),Room.reverseFacing(facing));
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
