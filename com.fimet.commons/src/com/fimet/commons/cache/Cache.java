package com.fimet.commons.cache;

import java.util.HashMap;
import java.util.Map;

public final class Cache {
	private static final Map<String, Object> cache = new HashMap<>();
	private Cache() {}
	public static Object get(String id) {
		return cache.get(id);
	}
	public static void add(String id, Object obj) {
		cache.put(id, obj);
	}
}
