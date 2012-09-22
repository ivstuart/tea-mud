package com.ivstuart.tmud.utils;

import java.util.List;

// TODO which is the better to use
public class ListGetter {

	public static Object find(List<?> list, String name) {
		int index = stringIndexOf(list, name, false);

		if (index < 0) {
			return null;
		}

		else {
			return list.get(index);
		}

	}

	public static int index(List<?> list, String name) {
		return stringIndexOf(list, name, false);
	}

	private static int stringIndexOf(List<?> list, String aString,
			boolean _indexLookup) {

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

		for (int index = 0; index < list.size(); index++) {

			String shortName = list.get(index).toString();

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
