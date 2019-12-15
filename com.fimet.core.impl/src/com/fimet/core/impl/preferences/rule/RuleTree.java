package com.fimet.core.impl.preferences.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.fimet.core.entity.sqlite.Rule;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class RuleTree extends TreeViewer {


	//private static Styler COLOR_ERROR_FIELD = StyledString.createColorRegistryStyler(JFacePreferences.ERROR_COLOR, null);
	private List<RuleNode> roots;
	private RulePage page;

	public RuleTree(RulePage page, Composite parent, int style) {
		super(parent, style);
		this.page = page;
		createContents(parent);
	}
	private void createContents(Composite parent) {
        setContentProvider(new ViewContentProvider());
        setLabelProvider(new DelegatingStyledCellLabelProvider(new RuleTreeLabelProvider()));
        createContextMenu();
	}
    public void setRules(List<Rule> ruleFormats) {
    	if (ruleFormats == null || ruleFormats.isEmpty()) {
    		roots = new ArrayList<>();
    	} else {
    		orderRules(ruleFormats);
    		roots = new ArrayList<>();
    		RuleNode mparent;
    		Iterator<Rule> iterator = ruleFormats.iterator();
    		Rule rnode = iterator.next();
    		while (rnode != null) {
    			mparent = new RuleNode(rnode);
    			roots.add(mparent);
    			rnode = addChildren(mparent, rnode.getOrder()+".", iterator);
			}
    	}
    	setInput(roots);
    }
    private Rule addChildren(RuleNode mparent, String starts, Iterator<Rule> iterator) {
    	if (iterator.hasNext()) {
    		Rule next = iterator.next();
    		while (next != null) {
	    		if (next.getOrder().startsWith(starts)) {
	    			next = addChildren(mparent.add(new RuleNode(next)), next.getOrder()+".", iterator);
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
    
    class ViewContentProvider implements ITreeContentProvider {
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }
        @Override
        public void dispose() {
        }
        @Override
        public Object[] getElements(Object inputElement) {
        	if (roots == null || roots.isEmpty()) {
        		return new RuleNode[0];
        	} else {
        		return roots.toArray(new RuleNode[roots.size()]);
        	}
        }
        @Override
        public Object[] getChildren(Object parentElement) {
            return ((RuleNode) parentElement).getChildren();
        }
        @Override
        public Object getParent(Object element) {
            return ((RuleNode) element).parent;
        }
        @Override
        public boolean hasChildren(Object element) {
            return ((RuleNode) element).hasChildren();
        }
    }
    /**
     * Creates the context menu
     *
     * @param viewer
     */
    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#RuleTreeViewerMenu","com.fimet.preferences.RuleTreeViewer"); //$NON-NLS-1$
        contextMenu.setRemoveAllWhenShown(true);
        contextMenu.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager mgr) {
                fillContextMenu(mgr);
            }
        });
        Menu menu = contextMenu.createContextMenu(this.getControl());
        this.getControl().setMenu(menu);
    }

    /**
     * Fill dynamic context menu
     *
     * @param contextMenu
     */
    private void fillContextMenu(IMenuManager contextMenu) {
        contextMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        contextMenu.add(new Action("New Rule") {
            @Override
            public void run() {
            	page.onNewRule();
            }
        });
        contextMenu.add(new Action("New Child") {
            @Override
            public void run() {
            	page.onNewChildRule();
            }
        });
        contextMenu.add(new Action("Edit Rule") {
            @Override
            public void run() {
            	page.onEditRule();
            }
        });
        contextMenu.add(new Action("Delete Rule") {
            @Override
            public void run() {
            	page.onDeleteRule();
            }
        });
        contextMenu.add(new Action("Up Rule") {
            @Override
            public void run() {
            	page.onUpRule();
            }
        });
        contextMenu.add(new Action("Down Rule") {
            @Override
            public void run() {
            	page.onDownRule();
            }
        });
    }
    public TreeItem getTreeItem(RuleNode node) {
    	if (node == null) {
    		return null;
    	}
    	if (node.parent == null) {
    		for (TreeItem item : getTree().getItems()) {
    			if (item.getData() == node) {
    				return item;
    			}
    		}
    		throw new NullPointerException();
    	} else {
			String[] split = node.rule.getOrder().split("\\.");
			String idRule = split[0];
			int deep = 1;
			TreeItem parent = null;
			for (TreeItem n : getTree().getItems()) {
				if (((RuleNode)n.getData()).rule.getOrder().equals(idRule)) {
					parent = n;
					break;
				}
			}
			if (parent == null) {
				throw new NullPointerException();
			}
			while (deep < split.length) {
				idRule = idRule + "." +split[deep++];  
				parent = findChildTreeItem(parent, idRule);
				if (parent == null) {
					throw new NullPointerException();
				}
			}
			return parent;
    	}
    }
    public TreeItem findChildTreeItem(TreeItem parent, String idRule) {
    	if (!parent.getExpanded()) {
    		parent.setExpanded(true);
    		refresh();
    	}
		for (TreeItem node : parent.getItems()) {
			if (((RuleNode)node.getData()).rule.getOrder().equals(idRule)) {
				return node;
			}
		}
		return null;
    }
    RuleNode getNodeInTree(String order) {
    	if (order == null)
    		throw new NullPointerException();
		String[] split = order.split("\\.");
		order = split[0] + "." +split[1];
		int deep = 2;
		RuleNode parent = null;
		for (RuleNode n : roots) {
			if (n.rule.getOrder().equals(order)) {
				parent = n;
			}
		}
		if (parent == null) {
			return null;
		}
		while (deep < split.length) {
			order = order + "." +split[deep++];  
			parent = getNodeInChilds(parent, order);
			if (parent == null) {
				return null;
			}
		}
		return parent;
    }
    private RuleNode getNodeInChilds(RuleNode parent, String idRule) {
    	if (parent.hasChildren()) {
			for (RuleNode node : parent.children) {
				if (node.rule.getOrder().equals(idRule)) {
					return node;
				}
			}
    	}
		return null;
    }
	public Rule getSelected() {
    	IStructuredSelection selection = (IStructuredSelection) this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return ((RuleNode)selection.getFirstElement()).rule;
    	}
    	return null;
	}
	public Rule getSelectedParent() {
    	IStructuredSelection selection = (IStructuredSelection) this.getSelection();
    	if (selection.getFirstElement() != null) {
    		RuleNode node = (RuleNode)selection.getFirstElement();
    		return node.parent != null ? node.parent.rule : null;
    	}
    	return null;
	}
	public RuleNode getSelectedParentNode() {
    	IStructuredSelection selection = (IStructuredSelection) this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return ((RuleNode)selection.getFirstElement()).parent;
    	}
		return null;
	}
	public RuleNode getSelectedNode() {
    	IStructuredSelection selection = (IStructuredSelection) RuleTree.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return (RuleNode)selection.getFirstElement();
    	}
    	return null;
    }
    public void add(RuleNode rule) {
		if (rule.parent == null) {
			if (!roots.contains(rule)) {
				roots.add(rule);
			}
		}
		refresh();
		getTree().deselectAll();
		TreeItem item = getTreeItem(rule);
		if (item != null)
			getTree().select(item);
	}
    public void update(RuleNode rule) {
    	rule.updateLabels();
		refresh();
		getTree().deselectAll();
		TreeItem item = getTreeItem(rule);
		if (item != null)
			getTree().select(item);
	}
	public void delete(RuleNode parent, RuleNode node) {
		if (parent == null) {
			int index = roots.indexOf(node);
			if (index != -1) {
				roots.remove(index);
				setInput(roots);
			}
		} else {
			parent.remove(node);
		}
    	refresh();
	}
	public List<RuleNode> getRoots(){
		return roots;
	}
	public Rule[] up(RuleNode rule) {
		TreeItem[] items = findLevel(getTree().getItems(), rule);
		int i;
		if (items != null && (i = contains(items, rule)) != -1 && i > 0) {
			Rule[] rules = permute(items, i, i-1);
			refresh();
			getTree().deselectAll();
			TreeItem item = getTreeItem(rule);
			if (item != null)
				getTree().select(item);
			return rules;
		}
		return null;
	}
	public Rule[] down(RuleNode rule) {
		TreeItem[] items = findLevel(getTree().getItems(), rule);
		int i;
		if (items != null && (i = contains(items, rule)) != -1 && i <= items.length) {
			Rule[] rules = permute(items, i, i+1);
			refresh();
			getTree().deselectAll();
			TreeItem item = getTreeItem(rule);
			if (item != null)
				getTree().select(item);
			return rules;
		}
		return null;
	}
	private TreeItem[] findLevel(TreeItem[] items, RuleNode rule) {
		if (contains(items, rule) != -1) {
			return items;
		}
		TreeItem[] found;
		for (TreeItem item : items) {
			if (item.getItemCount() > 0 && (found = findLevel(item.getItems(), rule)) != null) {
				return found;
			}
		}
		return null;
	}
	private Rule[] permute(TreeItem[] items, int i, int j) {
		if (items != null && items.length > 0 && i != j && i >= 0 && i < items.length && j >= 0 && j < items.length) {
			RuleNode rule1 = (RuleNode)items[i].getData();
			String order1 = rule1.rule.getOrder();
			RuleNode rule2 = (RuleNode)items[j].getData();
			rule1.rule.setOrder(rule2.rule.getOrder());
			rule2.rule.setOrder(order1);
			items[j].setData(rule1);
			items[i].setData(rule2);
			if (rule1.parent == null) {
				roots.set(i, rule2);
				roots.set(j, rule1);
			} else {
				rule1.parent.children.set(i, rule2);
				rule1.parent.children.set(j, rule1);
			}
			return new Rule[] {rule1.rule, rule2.rule};
		}
		return null;
	}
	private int contains(TreeItem[] items, RuleNode rule) {
		int i = 0;
		for (TreeItem item : items) {
			if (item.getData() == rule) return i;
			i++;
		}
		return -1;
	}
}
