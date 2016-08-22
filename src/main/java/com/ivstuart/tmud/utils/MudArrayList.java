package com.ivstuart.tmud.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.ivstuart.tmud.state.Prop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ivstuart.tmud.state.BasicThing;

/**
 * Decorator of List to allow following additional look up logic
 * 
 * get 1.sword get 2.sword
 * 
 * Hence when a list has multiple items with naming conflicts you can select
 * with an index specific to differentiate them
 * 
 * @author Ivan
 * 
 * @param <E>
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

		LOGGER.debug("Getting from list for:" + value);

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
				LOGGER.error("Client entered invalid index number" , e);
				itemNumber = -1;
			}
			value = value.substring(++indexOfSeperator, value.length());
		}

		return indexOf(value, itemNumber);
	}

	public int indexOf(String value, int itemNumber) {

		for (int index = 0; index < this.size(); index++) {

			// TODO merge Prop with BasicThing
			// String shortName = ((Prop) this.get(index)).getAlias();

//			if (shortName == null) {
//				shortName = ((BasicThing) this.get(index)).getId();
//			}

			String shortName = ((BasicThing) this.get(index)).getId();

			if (shortName == null) {
				continue;
			}

			boolean match = false;
			if (indexLookup) {
				match = (shortName.indexOf(value) > -1);
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

}
