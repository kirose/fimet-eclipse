package com.fimet.core.impl.preferences;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class History<E> {
	static final char INSERT = 'I';
	static final char UPDATE = 'U';
	static final char DELETE = 'D';
	Map<E, Modify> changes = new LinkedHashMap<E, Modify>();
	public History() {
	}
	public void insert(E entity) {
		modify(entity, INSERT);
	}
	public void update(E entity) {
		modify(entity, UPDATE);
	}
	public void delete(E entity) {
		if (changes.containsKey(entity) && changes.get(entity).first == INSERT) {
			changes.remove(entity);
		} else {
			modify(entity, DELETE);
		}
	}
	private void modify(E entity, char action) {
		if (changes.containsKey(entity)) {
			changes.get(entity).last(action);
		} else {
			changes.put(entity, new Modify(action));
		}
	}
	public List<E> getDeletes(){
		List<E> etities = new ArrayList<>();
		Modify m;
		for (Map.Entry<E, Modify> e : changes.entrySet()) {
			m = e.getValue();
			if (m.first != m.last) {
				if (m.first == UPDATE && m.last == DELETE) {
					etities.add(e.getKey());	
				}
			} else if (m.first == DELETE) {
				etities.add(e.getKey());
			}
		}
		return etities;
	}
	public List<E> getSaves(){
		List<E> etities = new ArrayList<>();
		Modify m;
		for (Map.Entry<E, Modify> e : changes.entrySet()) {
			m = e.getValue();
			if (m.first != m.last) {
				if (m.last != DELETE) {
					etities.add(e.getKey());		
				}
			} else if (m.first == INSERT || m.first == UPDATE) {
				etities.add(e.getKey());
			}
		}
		return etities;
	}
	public List<E> getUpdates(){
		List<E> etities = new ArrayList<>();
		Modify m;
		for (Map.Entry<E, Modify> e : changes.entrySet()) {
			m = e.getValue();
			if (m.first == m.last && m.first == UPDATE) {
				etities.add(e.getKey());
			}
		}
		return etities;
	}
	public List<E> getInserts(){
		List<E> etities = new ArrayList<>();
		Modify m;
		for (Map.Entry<E, Modify> e : changes.entrySet()) {
			m = e.getValue();
			if (m.first != m.last) {
				if (m.first == INSERT && m.last != DELETE) {
					etities.add(e.getKey());		
				}
			} else if (m.first == INSERT) {
				etities.add(e.getKey());
			}
		}
		return etities;
	}
	class Modify {
		char first;
		char last;
		Modify(char first){
			this.last = this.first = first;
		}
		void last(char action){
			last = action;
		}
	}
	public boolean isEmpty(Integer id) {
		return !changes.containsKey(id);
	}
	public boolean isEmpty() {
		return changes.isEmpty();
	}
}
