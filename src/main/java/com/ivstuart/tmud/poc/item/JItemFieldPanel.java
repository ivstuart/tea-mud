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

package com.ivstuart.tmud.poc.item;

import com.ivstuart.tmud.state.items.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

public class JItemFieldPanel extends JPanel {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<Method,JTextField> fieldMap = new HashMap<>();

    private static Map<Integer, Item> itemMap = new HashMap<>();

    // private static List<Item> itemList = new ArrayList<>();

    public JItemFieldPanel() {
    }

    public JItemFieldPanel(LayoutManager layout) {
        super(layout);
    }

    public static void addPressed() throws InvocationTargetException, IllegalAccessException {
        Item item;

        String itemName = JItemControlPanel.getDropDownValue();
        Class<?> itemClass = getItemClass(itemName);

        try {
            item = (Item) itemClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<Method,JTextField> entry: fieldMap.entrySet()) {

            Method setterMethod = entry.getKey();
            Class<?> type = setterMethod.getParameters()[0].getType();
            String value = entry.getValue().getText();

            if (value.isEmpty()) {
                continue;
            }

            if (type.isInstance("")) {
                setterMethod.invoke(item,value);
                continue;
            }

            if (type.getSimpleName().equals("int")) {
                setterMethod.invoke(item,Integer.parseInt(value));
                continue;
            }

            if (type.getSimpleName().equals("boolean")) {
                setterMethod.invoke(item,Boolean.parseBoolean(value));
                continue;
            }

        }
        int counter = JItemControlPanel.getRecordId();
        itemMap.put(counter, item);
        //itemList.add(counter,item);
    }

    public static void clearPressed() {
        for (JTextField textField : fieldMap.values()) {
            textField.setText("");
        }
    }

    public static void removePressed() {
        int counter = JItemControlPanel.getRecordId();
        itemMap.remove(counter);
        //itemList.remove(counter);
    }

    public static void recordUpdated() throws InvocationTargetException, IllegalAccessException {
        clearPressed();

        int counter = JItemControlPanel.getRecordId();

        Item item = itemMap.get(counter);

        if (item == null) {
            LOGGER.debug("No item at this record id:"+counter);
            return;
        }

        for (Map.Entry<Method,JTextField> entry: fieldMap.entrySet()) {

            Method setterMethod = entry.getKey();

            Class<?> type = setterMethod.getParameters()[0].getType();

            String getterName;
            if (type.getSimpleName().equals("boolean")) {
                getterName = setterMethod.getName().replaceFirst("set","is");
            }
            else {
                getterName = setterMethod.getName().replaceFirst("set","get");
            }

            Method getterMethod = null;
            try {
                getterMethod = item.getClass().getMethod(getterName);
            } catch (NoSuchMethodException e) {
                LOGGER.warn("No such method:"+getterName);
                continue;
            }

            Object response = getterMethod.invoke(item);

            if (response != null) {
                String value = response.toString();
                if (!value.startsWith("[")) {
                    entry.getValue().setText(response.toString());
                }
            }

        }


    }

    public static void clear() {
        fieldMap.clear();
    }

    public static Map<Integer,Item> getMap() {
        return itemMap;
    }

    public static void setMap(Map<Integer, Item> loadedMap) {
        itemMap = loadedMap;
        LOGGER.info("Setting map to be:"+itemMap);
    }

    public static void clearAndCreateUI() {
        LaunchItemEditor.getItemFieldPanel().removeAll();
        String itemName = JItemControlPanel.getDropDownValue();
        Class<?> itemClass = getItemClass(itemName);
        LaunchItemEditor.getItemFieldPanel().createFields(itemClass);
        LaunchItemEditor.repaint();
    }

    private static Class<?> getItemClass(String itemName) {
        try {
            return Class.forName("com.ivstuart.tmud.state.items." + itemName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void createFields(Class<?> aClass) {
        // Reflect to get method names and add these as labels

        List<Method> methodList = new ArrayList<>();

        methodList.addAll(Arrays.asList(aClass.getMethods()));
        Collections.sort(methodList, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                String name = o1.getName();
                String name2 = o2.getName();
                return name.compareTo(name2);
            }
        });


        for (Method method :  methodList) {
            String name = method.getName();
            if (name.startsWith("set")) {
                String paramString;
                Class<?> type = method.getParameters()[0].getType();
                if (method.getParameters().length > 1 ||
                        !type.isPrimitive() && !type.isInstance("") && !type.isEnum()) {
                    continue;
                }
                else {
                    paramString = method.getParameters()[0].getType().getSimpleName();
                }

                JLabel methodLabel = new JLabel(name +"("+paramString+")");
                JTextField methodField = new JTextField();
                this.add(methodLabel);
                this.add(methodField);
                fieldMap.put(method, methodField);
            }
        }
    }
}
