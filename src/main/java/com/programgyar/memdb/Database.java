package com.programgyar.memdb;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;

public class Database {
	private static MVMap<Object, PersistentData> map;

	public static void open() {
		if (map == null) {
			MVStore s = MVStore.open("db");
			s.setAutoCommitDelay(10000);
			map = s.openMap("data");
		}
	}

	public static <T extends PersistentData> T get(Class<T> clazz) {
		return get(clazz, null);
	}

	public static <T extends PersistentData> T get(Class<PersistentData> clazz, T defaultValue) {
		return get(clazz.getName(), defaultValue);
	}

	public static <T extends PersistentData> T get(Object key) {
		return get(key, null);
	}

	public static <T extends PersistentData> T get(Object key, T defaultValue) {
		if (map == null) {
			open();
		}
		PersistentData d = (PersistentData) map.get(key);
		if (d == null) {
			d = defaultValue;
			if (d != null) {
				put(key, d);
			}
		}
		if (d != null) {
			d.afterLoad();
		}
		return (T) d;
	}

	private static void put(Object key, PersistentData d) {
		d.beforeSave();
		map.put(key, d);
		d.afterSave();
	}

	public static void commit() {
		map.getStore().commit();
	}
}