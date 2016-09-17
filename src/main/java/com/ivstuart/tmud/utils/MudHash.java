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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

	public void add(String key, T aObject) {
		for (int i = 0; i < key.length(); i++) {
			String shorterKey = key.substring(0, i + 1);
			if (map.containsKey(shorterKey)) {
				continue;
			}
			map.put(shorterKey, aObject);
		}
	}

	public void clear() {
		map.clear();
	}

	public T get(String key) {
		T obj = map.get(key);
		if (obj == null) {
			return defaultObject;
		}
		return obj;
	}

	public Set<String> keySet() {
		return map.keySet();

	}

	public void remove(String key) {
		Set<String> keySet = map.keySet();

		Object objectToRemove = map.get(key);

		for (int i = 0; i < key.length(); i++) {
			String shorterKey = key.substring(0, i + 1);

			if (objectToRemove.equals(map.get(shorterKey))) {
				map.remove(shorterKey);
				keySet.remove(shorterKey);
			}
		}

		String firstCharacterOfKey = key.substring(0, 1);

		for (String keyOfMap : keySet) {
			if (keyOfMap.startsWith(firstCharacterOfKey)) {
				this.add(keyOfMap, map.get(keyOfMap));
				break;
			}
		}

	}

	public void replace(String aString, T replacement) {
		for (int i = 1; i < aString.length(); i++) {
			String truncatedKey = aString.substring(0, i + 1);
			map.put(truncatedKey, replacement);
		}
	}

	public void setDefault(T defaultObject) {
		this.defaultObject = defaultObject;
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
