package com.fimet.commons.history;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HistoryGroup<E> {
	static final char INSERT = 'I';
	static final char UPDATE = 'U';
	static final char DELETE = 'D';
	Map<E, Modify> changes = new LinkedHashMap<E, Modify>();
	List<Integer> ids = new ArrayList<>();
	public HistoryGroup() {
	}
	public void insert(Integer idGroup, E entity) {
		modify(idGroup, entity, INSERT);
	}
	public void update(Integer idGroup, E entity) {
		modify(idGroup, entity, UPDATE);
	}
	public void delete(Integer idGroup, E entity) {
		if (changes.containsKey(entity) && changes.get(entity).first == INSERT) {
			changes.remove(entity);
		} else {
			modify(idGroup, entity, DELETE);
		}
	}
	private void modify(Integer idGroup, E entity, char action) {
		if (!ids.contains(idGroup)) {
			ids.add(idGroup);
		}
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
	public List<Integer> getIds(){
		return ids;
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
		return !ids.contains(id);
	}
	public boolean isEmpty() {
		return changes.isEmpty();
	}
}
