package com.ivstuart.tmud.poc;

import javax.swing.*;
import java.awt.*;

public class JZoomPanel extends JPanel {

    private static JSlider silder;

    public void createSlider() {

        silder = new JSlider();

        silder.setMinimum(20);
        silder.setMaximum(200);
        silder.setName("Zoom");

        this.add(silder);

        silder.addChangeListener(e -> changed());
    }

    private void changed() {
        JWorldPanel.setResized();
        this.getRootPane().repaint();
    }

    public Dimension getPreferredSize() {
        return new Dimension(100,40);
    }

    public static int getValue() {
        return silder.getValue();
    }

}
