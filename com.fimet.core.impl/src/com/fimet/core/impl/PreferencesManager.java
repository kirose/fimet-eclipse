package com.fimet.core.impl;

import java.util.HashMap;
import java.util.Map;

import com.fimet.core.IPreferencesManager;
import com.fimet.core.entity.sqlite.Preference;
import com.fimet.persistence.sqlite.dao.PreferenceDAO;

public class PreferencesManager implements IPreferencesManager {
	private Map<String, Object> preferences = new HashMap<>();
	@Override
	public void free() {
		preferences.clear();
	}
	private <T>T getPreferenceValue(String name, Class<T> classValue) {
		Preference preference = PreferenceDAO.getInstance().findById(name);
		if (preference == null) {
			return null;
		}
		preferences.put(preference.getName(), parseValue(preference.getValue(), classValue));
		return classValue.cast(preferences.get(name));
	}
	private <T>T getPreferenceValue(String name, T defaultValue, Class<T> classValue) {
		Preference preference = PreferenceDAO.getInstance().findById(name);
		preferences.put(name, parseValue(preference != null ? preference.getValue() : defaultValue+"", classValue));
		return classValue.cast(preferences.get(name));
	}
	private Object parseValue(String value, Class<?> classValue) {
		if (classValue == String.class) {
			return value;
		} else if (classValue == int.class || classValue == Integer.class) {
			return Integer.parseInt(value);
		} else if (classValue == double.class || classValue == Double.class) {
			return Double.parseDouble(value);
		} else if (classValue == long.class || classValue == Long.class) {
			return Long.parseLong(value);
		} else if (classValue == boolean.class || classValue == Boolean.class) {
			return Boolean.parseBoolean(value);
		}
		return null;
	}
	public String getString(String name, String defaultValue) {
		if (preferences.containsKey(name)) {
			return (String)preferences.get(name);
		}
		return getPreferenceValue(name, defaultValue, String.class);
	}
	public String getString(String name) {
		if (preferences.containsKey(name)) {
			return (String)preferences.get(name);
		}
		return getPreferenceValue(name, String.class);
	}
	public void save(String name, String value) {
		preferences.put(name, value);
	}
	public Integer getInt(String name, Integer defaultValue) {
		if (preferences.containsKey(name)) {
			return (int)preferences.get(name);
		}
		return getPreferenceValue(name, defaultValue, Integer.class);
	}
	public Integer getInt(String name) {
		if (preferences.containsKey(name)) {
			return (Integer)preferences.get(name);
		}
		return getPreferenceValue(name, Integer.class);
	}
	public void save(String name, Integer value) {
		preferences.put(name, value);
	}
	public void save(String name, int value) {
		preferences.put(name, value);
	}
	public double getDouble(String name, double defaultValue) {
		if (preferences.containsKey(name)) {
			return (double)preferences.get(name);
		}
		return getPreferenceValue(name, defaultValue, Double.class);
	}
	public double getDouble(String name) {
		if (preferences.containsKey(name)) {
			return (double)preferences.get(name);
		}
		return getPreferenceValue(name, Double.class);
	}
	public void save(String name, double value) {
		preferences.put(name, value);
	}
	public Long getLong(String name, long defaultValue) {
		if (preferences.containsKey(name)) {
			return (Long)preferences.get(name);
		}
		return getPreferenceValue(name, defaultValue, Long.class);
	}
	public long getLong(String name) {
		if (preferences.containsKey(name)) {
			return (long)preferences.get(name);
		}
		return getPreferenceValue(name, Long.class);
	}
	public void save(String name, long value) {
		preferences.put(name, value);
	}
	public boolean getBoolean(String name, boolean defaultValue) {
		if (preferences.containsKey(name)) {
			return (boolean)preferences.get(name);
		}
		return getPreferenceValue(name, defaultValue, Boolean.class);
	}
	public boolean getBoolean(String name) {
		if (preferences.containsKey(name)) {
			return (boolean)preferences.get(name);
		}
		return getPreferenceValue(name, Boolean.class);
	}
	public void save(String name, boolean value) {
		preferences.put(name, value);
	}
	public void remove(String name) {
		preferences.remove(name);
		Preference p = PreferenceDAO.getInstance().findById(name);
		if (p != null) {
			PreferenceDAO.getInstance().delete(p);
		}
	}
	@Override
	public void saveState() {
		System.out.println("Saving preferences");
		PreferenceDAO dao = PreferenceDAO.getInstance();
		for (Map.Entry<String, Object> e : preferences.entrySet()) {
			dao.insertOrUpdate(new Preference(e.getKey(), e.getValue()+""));
		}		
	}

}
