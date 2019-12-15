package com.fimet.core.impl.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.entity.sqlite.Rule;
import com.fimet.core.net.ISocket;
import com.fimet.persistence.sqlite.dao.RuleDAO;

public class Ruler {
	private List<MatcherRule> roots;
	public Ruler(Enviroment enviroment, Integer type) {
		super();
		setRules(getRules(enviroment, type));
	}
    public void setRules(List<Rule> fieldFormats) {
    	if (fieldFormats == null || fieldFormats.isEmpty()) {
    		roots = new ArrayList<>();
    	} else {
    		orderRules(fieldFormats);
    		roots = new ArrayList<>();
    		MatcherRule mparent;
    		Iterator<Rule> iterator = fieldFormats.iterator();
    		Rule rnode = iterator.next();
    		while (rnode != null) {
    			roots.add(mparent = new MatcherRule(rnode));
    			rnode = addChildren(mparent, rnode.getOrder()+".", iterator);
			}
    	}
    }
    private Rule addChildren(MatcherRule mparent, String starts, Iterator<Rule> iterator) {
    	if (iterator.hasNext()) {
    		Rule next = iterator.next();
    		while (next != null) {
	    		if (next.getOrder().startsWith(starts)) {
	    			next = addChildren(mparent.add(new MatcherRule(next)), next.getOrder()+".", iterator);
	    		} else {
	    			return next;
	    		}
    		}
    		return next;
    	} else {
    		return null;
    	}
    }
    private void orderRules(List<Rule> formats) {
    	if (formats != null) {
			Collections.sort(formats, new Comparator<Rule>() {
				@Override
				public int compare(Rule f1, Rule f2) {
					return f1.getOrder().compareTo(f2.getOrder());
				}
			});
    	}
    }
	private List<Rule> getRules(Enviroment enviroment, Integer idField) {
		if (enviroment != null && idField != null) {
			return RuleDAO.getInstance().findByIdTypeAndField(enviroment.getIdType(), idField);
		} else {
			return null;
		}
	}
	protected MatcherRule getMatcher(ISocket connection) {
		for (MatcherRule match : roots) {
			if ((match = match.findMatch(connection)) != null) return match;
		}
		return null;
	}
	public Integer getIdResult(ISocket connection) {
		MatcherRule matcher = getMatcher(connection);
		if (matcher != null) {// MATCHES
			return matcher.getIdResult();
		}
		return null;// NO MATCHES
	}
	public String toString() {
		return roots != null ? roots.toString() : "";
	}
}
