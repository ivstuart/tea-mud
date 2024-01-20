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

/*
 * Created on Nov 23, 2004
 *
 *  To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ivstuart.tmud.person.carried;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ivan Stuart
 * <p>
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface SomeMoney extends Serializable {


    public boolean add(Money money);

    public void add(SomeMoney cash);

    public void clear();

    public List<SomeMoney> getList();

    public int getValue();

    public boolean isBag();

    public boolean isEmpty();

    public boolean remove(Money money);

    public boolean remove(MoneyBag cash);

    @Override
    public String toString();

    boolean remove(SomeMoney cost);

    int getWeight();

    SomeMoney removeAndConvert(int copper);

    /**
     * @param cash
     */

}
