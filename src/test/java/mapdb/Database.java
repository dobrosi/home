package mapdb;

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

	public static PersistentData load(Object key) {
		return load(key, null);
	}

	public static PersistentData load(Object key, PersistentData defaultValue) {
		PersistentData d = (PersistentData) map.get(key);
		if (d == null) {
			d = defaultValue;
			if (d != null) {
				save(key, d);
			}
		}
		if (d != null) {
			d.afterLoad();
		}
		return d;
	}

	private static void save(Object key, PersistentData d) {
		d.beforeSave();
		map.put(key, d);
		d.afterSave();
	}

	public static void commit() {
		map.getStore().commit();
	}
}