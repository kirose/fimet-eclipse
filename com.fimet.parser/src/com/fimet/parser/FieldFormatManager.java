package com.fimet.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fimet.core.IFieldFormatManager;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.entity.sqlite.FieldFormatGroup;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;
import com.fimet.persistence.sqlite.dao.FieldFormatGroupDAO;

public class FieldFormatManager implements IFieldFormatManager {
	
	private FieldFormatGroupDAO dao = FieldFormatGroupDAO.getInstance();
	private FieldFormatDAO daoFieldFormat = FieldFormatDAO.getInstance();
	private Map<Integer, FieldFormatGroup> cache = new HashMap<>();
	
	public FieldFormatGroup getGroup(Integer idGroup) {
		if (cache.containsKey(idGroup)) {
			return cache.get(idGroup);
		} else {
			FieldFormatGroup group = dao.findById(idGroup);
			FieldFormatGroup parent, child = group;
			while (child.getIdParent() != null) {
				child.setParent(parent = getGroup(child.getIdParent()));
				child = parent;
			}
			cache.put(idGroup, group);
			return group;
		}
	}

	public List<FieldFormat> getFieldsFormat(Integer idGroup) {
		List<FieldFormat> formats = getFieldsFormat(getGroup(idGroup), new ArrayList<>());
		Collections.sort(formats, (FieldFormat f1, FieldFormat f2) ->{return f1.getIdOrder().compareTo(f2.getIdOrder());});
		return formats;
	}
	private List<FieldFormat> getFieldsFormat(FieldFormatGroup group, List<Integer> exclude){
		List<FieldFormat> formats = daoFieldFormat.findByGroup(group.getId(), exclude);
		if (group.getParent() != null) {
			if (formats != null && !formats.isEmpty()) {
				Integer i;
				for (FieldFormat format : formats) {
					if (format.getIdFieldParent() == null) {
						i = Integer.parseInt(format.getIdField());
						if (!exclude.contains(i)) {
							exclude.add(i);
						}
					}
				}
			}
			List<FieldFormat> formatsParent = getFieldsFormat(group.getParent(), exclude);
			if (formatsParent != null && !formatsParent.isEmpty()) {
				for (FieldFormat format : formatsParent) {
					formats.add(format);
				}
			}
		}
		return formats;
	}
	@Override
	public List<FieldFormatGroup> getRootGroups() {
		List<Integer> ids = dao.findRootIds();
		List<FieldFormatGroup> roots = new ArrayList<>();
		for (Integer id : ids) {
			roots.add(getGroup(id));
		}
		return roots;
	}
	@Override
	public List<FieldFormatGroup> getGroups() {
		List<Integer> ids = dao.findAllIds();
		List<FieldFormatGroup> all = new ArrayList<>();
		for (Integer id : ids) {
			all.add(getGroup(id));
		}
		return all;
	}
	public List<FieldFormatGroup> getSubGroups(Integer idGroup) {
		List<Integer> ids = dao.findChildIds(idGroup);
		if (ids == null || ids.isEmpty()) {
			return null;
		}
		List<FieldFormatGroup> subgroups = new ArrayList<>();
		for (Integer id : ids) {
			subgroups.add(getGroup(id));
		}
		return subgroups;
	}
	@Override
	public void free() {
		cache.clear();
	}
	@Override
	public void saveState() {}
	public FieldFormatGroup saveGroup(FieldFormatGroup group) {
		FieldFormatGroupDAO.getInstance().insertOrUpdate(group);
		if (group.getId() == null) {
			FieldFormatGroup last = FieldFormatGroupDAO.getInstance().findLast();
			if (last != null)
				group.setId(last.getId());
		}
		return group;
	}
	public FieldFormatGroup deleteGroup(FieldFormatGroup group) {
		FieldFormatGroupDAO.getInstance().delete(group);
		FieldFormatDAO.getInstance().deleteByIdGroup(group);
		return group;
	}
	public FieldFormat saveField(FieldFormat field) {
		FieldFormatDAO.getInstance().insertOrUpdate(field);
		if (field.getIdFieldFormat() == null) {
			FieldFormat last = FieldFormatDAO.getInstance().findLast("idFieldFormat");
			if (last != null)
				field.setIdFieldFormat(last.getIdFieldFormat());
		}
		return field;
	}
	public FieldFormat deleteField(FieldFormat field) {
		FieldFormatDAO.getInstance().delete(field);
		return field;
	}

	@Override
	public List<FieldFormat> getFieldsFormatOnlyGroup(Integer idGroup) {
		return daoFieldFormat.findByGroup(idGroup, null);
	}

	@Override
	public void free(List<Integer> groups) {
		Manager.get(IParserManager.class).free(groups);
		for (Integer idGroup : groups) {
			FieldFormatGroup group = cache.remove(idGroup);
			if (group != null) {
				group.setParent(null);
			}
		}
	}

	@Override
	public Integer getNextIdGroup() {
		return FieldFormatGroupDAO.getInstance().getNextSequenceId();
	}
	@Override
	public Integer getPrevIdGroup() {
		return FieldFormatGroupDAO.getInstance().getPrevSequenceId();
	}
}
