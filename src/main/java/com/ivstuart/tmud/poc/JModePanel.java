package com.ivstuart.tmud.poc;

import javax.swing.*;
import java.awt.*;

public class JModePanel extends JPanel {

    private static JCheckBox editDoors;
    private static JCheckBox editRooms;
    private static JCheckBox editExits;

    private static JCheckBox oneWay;
    private static JCheckBox open;
    private static JCheckBox path;

    private static JTextField drawPlane;

    private static JTextField zoneField;

    public static boolean isEditDoors() {
        return editDoors.isSelected();
    }

    public static boolean isEditRooms() {
        return editRooms.isSelected();
    }

    public static boolean isEditExits() {
        return editExits.isSelected();
    }

    public static boolean isBidirectional() {
        return !oneWay.isSelected();
    }

    public static boolean isOpen() {
        return open.isSelected();
    }

    public static boolean isPath() {
        return path.isSelected();
    }

    public static int getValue() {
        return Integer.parseInt(drawPlane.getText());
    }

    public static int getZone() {

        if (zoneField == null) {
            return 0;
        }

        return Integer.parseInt(zoneField.getText());
    }

    public void createInterface() {
        JButton addMob = new JButton("Add Mob");

        editDoors = new JCheckBox("Edit doors");
        editRooms = new JCheckBox("Edit rooms");
        editExits = new JCheckBox("Edit exits");
        oneWay = new JCheckBox("One way");
        open = new JCheckBox("Open");
        path = new JCheckBox("Path");

        drawPlane = new JTextField("0");

        JLabel level = new JLabel("Level:");

        this.add(addMob);

        this.add(new Label("Zone:"));

        zoneField = new JTextField("0");
        this.add(zoneField);


        this.add(editDoors);
        this.add(editRooms);
        this.add(editExits);
        this.add(oneWay);
        this.add(open);
        this.add(path);

        this.add(level);
        this.add(drawPlane);

        drawPlane.addActionListener(e -> changedValue());

        addMob.addActionListener(e -> buttonPressed());
    }

    private void buttonPressed() {

        JFrame frame = new JFrame();

        JMobAdderPanel mobAdder = new JMobAdderPanel(new GridLayout(6,2));
        mobAdder.createWidget();

        frame.add(mobAdder);

        frame.setTitle("Mob Adder");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(200,400));
        frame.pack();
    }

    private void changedValue() {
        this.getRootPane().repaint();
    }
}
