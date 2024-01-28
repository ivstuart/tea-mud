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
