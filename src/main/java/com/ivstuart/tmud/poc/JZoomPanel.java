package com.ivstuart.tmud.poc;

import javax.swing.*;
import java.awt.*;

public class JZoomPanel extends JPanel {

    private static JSlider jSlider;

    public static int getValue() {
        return jSlider.getValue();
    }

    public void createSlider() {

        jSlider = new JSlider();

        jSlider.setMinimum(20);
        jSlider.setMaximum(200);
        jSlider.setName("Zoom");

        this.add(jSlider);

        jSlider.addChangeListener(e -> changed());
    }

    private void changed() {
        JWorldPanel.setResized();
        this.getRootPane().repaint();
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 40);
    }

}
