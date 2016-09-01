/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.world;

import java.util.LinkedList;
import java.util.List;

/**
 * @author stuarti
 *         <p>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Channel {

    private static final int MAX_SIZE = 20;

    private List<String> good;

    // TODO
    private List<String> evil;

    /**
     *
     */
    public Channel() {
        super();
        good = new LinkedList<String>();
        evil = new LinkedList<String>();
    }

    public void add(String message, boolean isGood) {

        if (isGood) {
            add(good, message);
        } else {
            add(evil, message);
        }

    }

    private void add(List<String> list, String message) {
        list.add(message);

        if (list.size() > MAX_SIZE) {
            list.remove(0);
        }
    }

    public String toString(boolean isGood) {
        if (isGood) {
            return toString(good);
        } else {
            return toString(evil);
        }
    }

    private String toString(List<String> list) {
        StringBuilder sb = new StringBuilder();

        for (String lineOfChat : list) {
            sb.append(lineOfChat).append("\n");
        }

        return sb.toString();
    }
}
