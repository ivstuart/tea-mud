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
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class JBitsPanel extends JPanel {

    private static final JCheckBox[] boxes = new JCheckBox[PlaceFlags.NUMBER_OF_FLAGS];

    public JBitsPanel(GridLayout gridLayout) {
        super(gridLayout);
    }

    public static List<Integer> getValues() {
        List<Integer> myListOfBits = new ArrayList<>();

        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i].isSelected()) {
                myListOfBits.add(i);
            }
        }
        return myListOfBits;

    }

    public static void setValues(BitSet bitSet) {

        if (bitSet == null) {
            for (JCheckBox box : boxes) {
                box.setSelected(false);
            }
            return;
        }

        for (int i = 0; i < boxes.length; i++) {

            boxes[i].setSelected(bitSet.get(i));
        }

    }

    public void createComponents() {

        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new JCheckBox(PlaceFlags.getBitName(i));
            this.add(boxes[i]);

            final int index = i;
            boxes[i].addActionListener(e -> changeValue(index));

        }
    }

    private void changeValue(int i) {
        WorldMap.getSelectedRoom().getRoomFlags().toggleFlag(i);
    }

}
