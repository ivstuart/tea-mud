package com.ivstuart.tmud.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Wrapper class to provide an effective ConcurrentHashSet
 * 
 * @author Ivan Stuart
 * 
 * @param <V>
 */
public class ReadWriteSet<V> {

	public static void main(String args[]) {

		Map<Integer, Object> mapId = new IdentityHashMap<Integer, Object>();

		mapId.put(new Integer(5).intValue(), "another five");

		mapId.put(5, "five");

		System.out.println("data = " + mapId);

		Map<String, Object> mapIdString = new IdentityHashMap<String, Object>();

		mapIdString.put("5", "five1");

		mapIdString.put("5", "five2");

		mapIdString.put(new String("5").intern(), "five3");

		System.out.println("data = " + mapIdString);
	}

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	private final Lock readLock = lock.readLock();

	private final Set<V> set;

	private final Lock writeLock = lock.writeLock();

	public ReadWriteSet(Set<V> set) {
		this.set = set;
	}

	public boolean add(V value) {
		writeLock.lock();

		try {
			return set.add(value);
		} finally {
			writeLock.unlock();
		}
	}

	public boolean addAll(Collection<? extends V> c) {
		if (c.isEmpty()) {
			return true;
		}
		writeLock.lock();

		try {
			return set.addAll(c);
		} finally {
			writeLock.unlock();
		}

	}

	public void clear() {
		writeLock.lock();

		try {
			set.clear();
		} finally {
			writeLock.unlock();
		}
	}

	public boolean contains(Object o) {
		readLock.lock();

		try {
			return set.contains(o);
		} finally {
			readLock.unlock();
		}
	}

	public boolean containsAll(Collection<?> c) {
		readLock.lock();

		try {
			return set.containsAll(c);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean equals(Object o) {
		readLock.lock();

		try {
			return set.equals(o);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int hashCode() {
		readLock.lock();

		try {
			return set.hashCode();
		} finally {
			readLock.unlock();
		}
	}

	public boolean isEmpty() {
		readLock.lock();

		try {
			return set.isEmpty();
		} finally {
			readLock.unlock();
		}
	}

	public Iterator<V> iterator() {
		return Collections.unmodifiableCollection(set).iterator();
	}

	public boolean remove(Object o) {
		writeLock.lock();

		try {
			return set.remove(o);
		} finally {
			writeLock.unlock();
		}
	}

	public boolean removeAll(Collection<?> c) {
		writeLock.lock();

		try {
			return set.removeAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	public boolean retainAll(Collection<?> c) {
		writeLock.lock();

		try {
			return set.retainAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	public int size() {
		readLock.lock();

		try {
			return set.size();
		} finally {
			readLock.unlock();
		}
	}

	public Object[] toArray() {
		readLock.lock();

		try {
			return set.toArray();
		} finally {
			readLock.unlock();
		}
	}

	public <T> T[] toArray(T[] a) {
		readLock.lock();

		try {
			return set.toArray(a);
		} finally {
			readLock.unlock();
		}
	}
}
