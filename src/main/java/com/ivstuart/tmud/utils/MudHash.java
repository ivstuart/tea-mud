package com.ivstuart.tmud.utils;

import java.util.*;

/**
 * 
 * @author Ivan Stuart
 * 
 *         This utility class is used to store command objects and the various
 *         shorten versions of there name. e.g. south is referenced by
 *         s,so,sou,sout and south string keys.
 */
public class MudHash<T> {

	private Map<String, T> map;

	private T defaultObject;

	public MudHash() {
		map = new HashMap<String, T>();
	}

	public void add(String aString, T aObject) {
		for (int i = 0; i < aString.length(); i++) {
			String aTempString = aString.substring(0, i + 1);
			if (map.containsKey(aTempString)) {
				continue;
			}
			map.put(aTempString, aObject);
		}
	}

	public void clear() {
		map.clear();
	}

	public T get(String aString) {
		T obj = map.get(aString);
		if (obj == null) {
			return defaultObject;
		}
		return obj;
	}

	public Set<String> keySet() {
		return map.keySet();

	}

	public void remove(String aString) {
		Set<String> keySet = map.keySet();
		Object aObject = map.get(aString);
		for (int i = 0; i < aString.length(); i++) {
			String aTempString = aString.substring(0, i + 1);
			if (aObject == map.get(aTempString)) {
				map.remove(aTempString);
				keySet.remove(aTempString);
			}
		}
		String aTemp = aString.substring(0, 1);
		Iterator<String> aIterator = keySet.iterator();
		while (aIterator.hasNext()) {
			String aKeyString = aIterator.next();
			if (aKeyString.startsWith(aTemp)) {
				this.add(aKeyString, map.get(aKeyString));
				break;
			}
		}

	}

	public void replace(String aString, T aObject) {
		for (int i = 1; i < aString.length(); i++) {
			String aTempString = aString.substring(0, i + 1);
			map.put(aTempString, aObject);
		}
	}

	public void setDefault(T obj) {
		defaultObject = obj;
	}

	@Override
	public String toString() {
		return "map [ " + map.toString() + " ]\n" + "default [ "
				+ defaultObject + " ]";
	}

	public Collection<T> values() {
		return map.values();
	}
}
