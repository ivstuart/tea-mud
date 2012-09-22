package com.ivstuart.tmud.utils;

import java.util.Collection;

public class CollectionUtils {

	public static boolean isEmpty(Collection<?> col) {
		return (col == null || col.isEmpty());
	}
}
