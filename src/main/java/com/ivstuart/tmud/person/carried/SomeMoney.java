/*
 * Created on Nov 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ivstuart.tmud.person.carried;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ivan Stuart
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface SomeMoney extends Serializable {

	public void add(Money money);

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

	/**
	 * @param cash
	 */

}
