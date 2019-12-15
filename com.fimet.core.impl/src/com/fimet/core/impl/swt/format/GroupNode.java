package com.fimet.core.impl.swt.format;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;

import com.fimet.commons.Color;
import com.fimet.commons.DefaultStyler;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IFieldFormatManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldFormatGroup;

public class GroupNode {
	static Styler STYLER_ID_GROUP;
	static Styler STYLER_NAME_GROUP;
	static {
		init();
	}
	private static void init() {
		if (STYLER_ID_GROUP == null) {
			STYLER_ID_GROUP = new DefaultStyler(Color.GRAY, null);//StyledString.COUNTER_STYLER;
			STYLER_NAME_GROUP = StyledString.createColorRegistryStyler(JFacePreferences.HYPERLINK_COLOR, null);
		}
	}
	static IFieldFormatManager fieldFormatGroupManager = Manager.get(IFieldFormatManager.class);
	FieldFormatGroup group;
	GroupNode parent;
	List<GroupNode> children;
	GroupNode(FieldFormatGroup group){
		this.group = group;
		
		setChildren(group.getId());
	}
	public FieldFormatGroup getGroup() {
		return group;
	}
	public StyledString getStyledText() {
    	StyledString s;
		s = new StyledString();
		s.append("["+group.getId()+"]", STYLER_ID_GROUP);
		s.append("["+StringUtils.maxLength(group.getName()+"", 40)+"]", STYLER_NAME_GROUP);
    	return s;
	}
	private void setChildren(Integer idGroupParent) {
		List<FieldFormatGroup> children = fieldFormatGroupManager.getSubGroups(idGroupParent);
		if (children != null && !children.isEmpty()) {
			this.children = new ArrayList<>();
			GroupNode node;
			for (FieldFormatGroup group : children) {
				node = new GroupNode(group);
				node.parent = this;
				this.children.add(node);
			}
		}
	}
	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}
	public GroupNode[] getChildren() {
		return hasChildren() ? children.toArray(new GroupNode[children.size()]): null;
	}
	public GroupNode add(FieldFormatGroup group) {
		if (children == null)
			children = new ArrayList<>();
		GroupNode node = new GroupNode(group);
		node.parent = this;
		children.add(node);
		return node;
	}
	public GroupNode getNode(FieldFormatGroup group) {
		if (hasChildren()) {
			for (GroupNode node : children) {
				if (node.group.equals(group)) {
					return node;
				}
			}
		}
		return null;
	}
	public GroupNode remove(FieldFormatGroup group) {
		GroupNode node = getNode(group);
		if (node != null) {
			children.remove(node);
			node.parent = null;
			return node;
		}
		return null;
	}
	public GroupNode remove(GroupNode group) {
		if (hasChildren()) {
			int index = children.indexOf(group);
			if (index != -1) {
				GroupNode node = children.get(index);
				children.remove(node);
				node.parent = null;
				return node;
			}
		}
		return null;
	}
}
