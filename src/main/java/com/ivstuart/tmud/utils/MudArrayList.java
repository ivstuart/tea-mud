/*
 *  Copyright 2016. Ivan Stuart
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

package com.ivstuart.tmud.utils;

import com.ivstuart.tmud.state.BasicThing;
import com.ivstuart.tmud.state.Prop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Decorator of List to allow following additional look up logic
 * <p>
 * get 1.sword get 2.sword
 * <p>
 * Hence when a list has multiple items with naming conflicts you can select
 * with an index specific to differentiate them
 *
 * @param <E>
 * @author Ivan
 */
public class MudArrayList<E> extends ArrayList<E> {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final long serialVersionUID = 1L;
    private boolean indexLookup = false;

    public MudArrayList() {
        super(0);
    }

    public MudArrayList(boolean indexLookup) {
        super(0);
        this.indexLookup = indexLookup;
    }

    public MudArrayList(Collection<? extends E> collection) {
        this.addAll(collection);
    }

    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    public boolean containsString(String containedString) {

        for (E element : this) {

            if (containedString.equalsIgnoreCase(element.toString())) {
                return true;
            }

        }

        return false;

    }

    public E get(String value) {

        // LOGGER.debug("Getting from list for:" + value);

        int index = this.stringIndexOf(value);

        if (index < 0) {
            return null;
        } else {
            return this.get(index);
        }
    }

    public E getExact(String value) {

        for (E element : this) {

            if (value.equalsIgnoreCase(element.toString())) {
                return element;
            }
        }

        return null;
    }

    public E remove(String value) {
        int index = this.stringIndexOf(value);
        if (index < 0) {
            return null;
        } else {
            return this.remove(index);
        }
    }

    public E removeExact(String value) {
        for (int index = 0; index < this.size(); index++) {

            String stringValue = this.get(index).toString();

            if (value.equalsIgnoreCase(stringValue)) {
                return this.remove(index);
            }
        }
        return null;

    }

    public int stringIndexOf(String value) {

        int indexOfSeperator = value.indexOf('.');

        if (indexOfSeperator == 0 || indexOfSeperator == value.length()) {
            return -1;
        }

        int itemNumber = -1;

        if (indexOfSeperator > 0) {
            try {
                itemNumber = Integer.parseInt(value.substring(0,
                        indexOfSeperator));
            } catch (NumberFormatException e) {
                LOGGER.error("Client entered invalid index number", e);
            }
            value = value.substring(++indexOfSeperator);
        }

        return indexOf(value, itemNumber);
    }

    public int indexOf(String value, int itemNumber) {

        for (int index = 0; index < this.size(); index++) {

            E object = this.get(index);

            String shortName = null;

            if (object instanceof Prop) {
                shortName = ((Prop) object).getAlias();
            } else {
                shortName = ((BasicThing) object).getId();
            }

            if (shortName == null) {
                continue;
            }

            boolean match = false;
            if (indexLookup) {
                match = (shortName.contains(value));
            } else {
                match = shortName.startsWith(value);
            }

            if (match) {
                if (itemNumber-- <= 1) {
                    return index;
                }
            }
        }
        return -1;
    }

    public E removeRandom() {
        if (size() == 0) {
            return null;
        }
        return this.get((int) (size() * Math.random()));
    }
}
