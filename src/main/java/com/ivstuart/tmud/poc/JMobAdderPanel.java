package com.ivstuart.tmud.poc;

import javax.swing.*;
import java.awt.*;

public class JMobAdderPanel extends JPanel {

    private static final String[] labels = {
            "Name",
            "Level",
            "Rate",
            "XP modifier",
            "Item modifier",
            "Gold modifier"};

    public JMobAdderPanel(GridLayout gridLayout) {
        super(gridLayout);
    }

    public void createWidget() {

        JLabel jLabel;
        JTextField jTextField;

        for (String label : labels) {
            jLabel = new JLabel(label);
            jTextField = new JTextField();

            this.add(jLabel);
            this.add(jTextField);

        }

    }
}
