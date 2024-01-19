package com.ivstuart.tmud.poc;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class JBitsPanel extends JPanel {

    private static JCheckBox[] boxes = new JCheckBox[20];

    private static String[] bitNames = {"No Summon", "Bit 1", "2", "3","4","5","6"};

    public JBitsPanel(GridLayout gridLayout) {
        super(gridLayout);
    }

    public void createComponents(){

        for (int i=0;i<bitNames.length;i++) {
            boxes[i] = new JCheckBox(bitNames[i]);
            this.add(boxes[i]);

            final int index = i;
            boxes[i].addActionListener(e -> changeValue(index));

        }
    }

    private void changeValue(int i) {
        World.getSelectedRoom().getRoomFlags().toggleFlag(i);
    }

    public static List<Integer> getValues() {
        List<Integer> myListOfBits = new ArrayList<Integer>();

        for (int i=0;i<bitNames.length;i++) {
            if (boxes[i].isSelected()) {
                myListOfBits.add(i);
            }
        }
        return myListOfBits;

    }

    public static void setValues(BitSet bitSet) {

        if (bitSet == null) {
            for (int i=0;i<bitNames.length; i++) {
                boxes[i].setSelected(false);
            }
            return;
        }

        for (int i=0;i<bitNames.length; i++) {

            if (bitSet.get(i)) {
                boxes[i].setSelected(true);
            }
            else {
                boxes[i].setSelected(false);
            }
        }

    }

}
