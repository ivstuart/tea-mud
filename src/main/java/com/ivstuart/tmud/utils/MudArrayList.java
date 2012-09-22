package com.ivstuart.tmud.utils;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.state.BasicThing;

public class MudArrayList<E> extends ArrayList<E> {

	private static final Logger LOGGER = Logger.getLogger(MudArrayList.class);

	private static final long serialVersionUID = 1L;
	private boolean _indexLookup = false;

	public MudArrayList() {
		super(0);
	}

	public MudArrayList(boolean indexLookup_) {
		super(0);
		_indexLookup = indexLookup_;
	}

	public MudArrayList(Collection<? extends E> collection) {
		this.addAll(collection);
	}

	@Override
	public boolean add(E e) {


		return super.add(e);
	}

	public boolean containsString(String aString) {
		for (int index = 0; index < this.size(); index++) {

			String stringValue = this.get(index).toString();

			if (aString.equalsIgnoreCase(stringValue)) {
				return true;
			}
		}
		return false;
	}

	public E get(String msg_) {

		LOGGER.info("Getting from list for:" + msg_);

		int index = this.stringIndexOf(msg_);

		if (index < 0) {
			return null;
		} else {
			return this.get(index);
		}
	}

	public E getExact(String aString) {
		for (int index = 0; index < this.size(); index++) {

			String stringValue = this.get(index).toString();

			if (aString.equalsIgnoreCase(stringValue)) {
				return this.get(index);
			}
		}
		return null;
	}

	public E remove(String aString) {
		int index = this.stringIndexOf(aString);
		if (index < 0) {
			return null;
		} else {
			return this.remove(index);
		}
	}

	public E removeExact(String aString) {
		for (int index = 0; index < this.size(); index++) {

			String stringValue = this.get(index).toString();

			if (aString.equalsIgnoreCase(stringValue)) {
				return this.remove(index);
			}
		}
		return null;

	}

	private int stringIndexOf(String aString) {

		int indexOfSeperator = aString.indexOf('.');

		if (indexOfSeperator == 0 || indexOfSeperator == aString.length()) {
			return -1;
		}

		int itemNumber = -1;

		if (indexOfSeperator > 0) {
			try {
				itemNumber = Integer.parseInt(aString.substring(0,
						indexOfSeperator));
			} catch (Exception e) {
				itemNumber = -1;
			}
			aString = aString.substring(++indexOfSeperator, aString.length());
		}

		for (int index = 0; index < this.size(); index++) {

			// TODO do not use toString();

			BasicThing basicThing = (BasicThing) this.get(index);

			String shortName = basicThing.getId();

			if (shortName != null
					&& (shortName.startsWith(aString) || (_indexLookup && shortName
							.indexOf(aString) > -1))) {
				if (itemNumber-- < 2) {
					return index;
				}
			}
		}
		return -1;
	}

}
