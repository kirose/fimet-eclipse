package com.fimet.core.impl.swt.format;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.fimet.commons.exception.FimetException;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.entity.sqlite.FieldFormatGroup;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FieldTree extends TreeViewer {


	//private static Styler COLOR_ERROR_FIELD = StyledString.createColorRegistryStyler(JFacePreferences.ERROR_COLOR, null);
	private List<FieldNode> roots;
	private IFieldController controller;
	private FieldFormatGroup groupLoaded;

	public FieldTree(IFieldController controller, Composite parent, int style) {
		super(parent, style);
		this.controller = controller;
		this.roots = new ArrayList<>();
		createContents(parent);
	}
	private void createContents(Composite parent) {
        setContentProvider(new ViewContentProvider());
        setLabelProvider(new DelegatingStyledCellLabelProvider(new FieldTreeLabelProvider()));
        getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        createContextMenu();
	}
	public FieldFormatGroup getGroupLoaded() {
		return groupLoaded;
	}
	public void setGroupLoaded(FieldFormatGroup g) {
		groupLoaded = g;
	}
    public void setFieldFormats(List<FieldFormat> fieldFormats) {
    	if (fieldFormats == null || fieldFormats.isEmpty()) {
    		roots = new ArrayList<>();//new FieldNode[0];
    	} else {
    		orderFieldFormats(fieldFormats);
    		List<FieldNode> rootsList = new ArrayList<>();
    		FieldNode node;
    		Iterator<FieldFormat> iterator = fieldFormats.iterator();
    		FieldFormat fieldFormat = iterator.next();
    		for (;;) {
				node = new FieldNode(fieldFormat);
				if (fieldFormat.getIdFieldParent() == null || fieldFormat.getIdFieldParent().length() == 0) {
					rootsList.add(node);
				}
				if (fieldFormat.getChilds() != null) {
					fieldFormat = addChilds(iterator, node);
					if (fieldFormat == null) {
						break;
					}
				} else {
					if (iterator.hasNext()) {
						fieldFormat = iterator.next();
					} else {
						break;
					}
				}
			}
    		roots = rootsList;
    	}
    	setInput(roots);
    }
    private void orderFieldFormats(List<FieldFormat> formats) {
    	if (formats != null) {
			Collections.sort(formats, new Comparator<FieldFormat>() {
				@Override
				public int compare(FieldFormat f1, FieldFormat f2) {
					return f1.getIdOrder().compareTo(f2.getIdOrder());
				}
			});
    	}
    }
    private FieldFormat addChilds(Iterator<FieldFormat> iterator, FieldNode parent) {
    	if (!iterator.hasNext()) {
    		return null;
    	}
    	FieldFormat fieldFormat = iterator.next();
    	FieldNode child;
		while (true) {
			if (
				(fieldFormat.getIdFieldParent() != null && fieldFormat.getIdFieldParent().equals(parent.field.getIdField())) ||
				(fieldFormat.getIdField().startsWith(parent.field.getIdField()) && fieldFormat.getIdField().indexOf('.',parent.field.getIdField().length()+1) == -1)
			) {
				child = new FieldNode(fieldFormat);
				child.parent = parent;
				parent.children.add(child);
				if (fieldFormat.getChilds() != null) {
					fieldFormat = addChilds(iterator, child);
				} else if (iterator.hasNext()){
					fieldFormat = iterator.next();
				} else {
					fieldFormat = null;
				}
				if (fieldFormat == null) {
					return null;
				}
			} else {
				return fieldFormat;
			}
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
        		return new FieldNode[0];
        	} else {
        		return roots.toArray(new FieldNode[roots.size()]);
        	}
        }
        @Override
        public Object[] getChildren(Object parentElement) {
            return ((FieldNode) parentElement).getChildren();
        }
        @Override
        public Object getParent(Object element) {
            return ((FieldNode) element).parent;
        }
        @Override
        public boolean hasChildren(Object element) {
            return ((FieldNode) element).hasChildren();
        }
    }
    /**
     * Creates the context menu
     *
     * @param viewer
     */
    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#FieldFormatTreeViewerMenu","com.fimet.preferences.FieldFormatTreeViewer"); //$NON-NLS-1$
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
        contextMenu.add(new Action("New Field") {
            @Override
            public void run() {
            	controller.onNewField();
            }
        });
        contextMenu.add(new Action("Edit Field") {
            @Override
            public void run() {
            	controller.onEditField();
            }
        });
        contextMenu.add(new Action("Delete Field") {
            @Override
            public void run() {
            	controller.onDeleteField();
            }
        });
    }
    public TreeItem getTreeItem(FieldNode node) {
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
			String[] split = node.field.getIdField().split("\\.");
			String idField = split[0];
			int deep = 1;
			TreeItem parent = null;
			for (TreeItem n : getTree().getItems()) {
				if (((FieldNode)n.getData()).field.getIdField().equals(idField)) {
					parent = n;
					break;
				}
			}
			if (parent == null) {
				throw new NullPointerException();
			}
			while (deep < split.length) {
				idField = idField + "." +split[deep++];  
				parent = findChildTreeItem(parent, idField);
				if (parent == null) {
					throw new NullPointerException();
				}
			}
			return parent;
    	}
    }
    public TreeItem findChildTreeItem(TreeItem parent, String idField) {
    	if (!parent.getExpanded()) {
    		parent.setExpanded(true);
    		refresh();
    	}
		for (TreeItem node : parent.getItems()) {
			if (((FieldNode)node.getData()).field.getIdField().equals(idField)) {
				return node;
			}
		}
		return null;
    }
    public FieldNode getTreeNode(String idField) {
    	if (idField == null)
    		throw new NullPointerException();
		if (idField.indexOf('.') == -1) {
			return getRoot(idField);
		} else {
			String[] ids = idField.split("\\.");
			FieldNode root = getRoot(ids[0]);
			if (root == null) return null;
			String id = ids[0];
			FieldNode parent = root;
			for (int i = 1; i < ids.length; i++) {
				id += "."+ids[i];
				parent = getNode(parent, id);
				if (parent == null) return null;
			}
			return parent;
		}
    }
	private FieldNode getRoot(String idField) {
		for (FieldNode node : roots) {
			if (idField.equals(node.getField().getIdField())) {
				return node;
			}
		}
		return null;
	}
	private FieldNode getNode(FieldNode parent, String idField) {
		if (parent.hasChildren()) {
			for (FieldNode node : parent.getChildren()) {
				if (idField.equals(node.getField().getIdField())) {
					return node;
				}
			}
		}
		return null;
	}
	public FieldFormat getSelected() {
    	IStructuredSelection selection = (IStructuredSelection) this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return ((FieldNode)selection.getFirstElement()).field;
    	}
    	return null;
	}
	public FieldFormat getSelectedParent() {
    	IStructuredSelection selection = (IStructuredSelection) this.getSelection();
    	if (selection.getFirstElement() != null) {
    		FieldNode node = (FieldNode)selection.getFirstElement();
    		return node.parent != null ? node.parent.field : null;
    	}
    	return null;
	}
	public FieldNode getSelectedParentNode() {
    	IStructuredSelection selection = (IStructuredSelection) this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return ((FieldNode)selection.getFirstElement()).parent;
    	}
		return null;
	}
	public FieldNode getSelectedNode() {
    	IStructuredSelection selection = (IStructuredSelection) FieldTree.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return (FieldNode)selection.getFirstElement();
    	}
    	return null;
    }
	public int indexOf(FieldFormat field) {
		int i = 0;
		for (FieldNode node : roots) {
			if (node.field.equals(field)) {
				return i;
			}
			i++;
		}
		return -1;
	}
    public void update(FieldNode node) {
    	FieldTree.this.internalExpand(node, true);
		refresh();
		getTree().deselectAll();
		select(node.getField().getIdField());
	}
	public void delete(FieldNode parent, FieldNode node) {
		if (parent == null) {
			int index = roots.indexOf(node);
			if (index != -1) {
				roots.remove(index);
			}
		} else {
			parent.remove(node);
		}
    	refresh();
	}
    public void add(FieldFormat node) {
    	FieldNode safe = getNodeSafe(node);
    	safe.field = node;
    	if (safe.parent != null) {
    		FieldTree.this.internalExpand(safe, true);
    	}
    	refresh();
    	getTree().deselectAll();
		select(node.getIdField());
    }
	public FieldNode getNodeSafe(FieldFormat f) {
		if (f == null || f.getIdField()== null) {
			return null;
		}
		if (f.getIdField().indexOf('.') == -1) {
			FieldNode root = getRoot(f.getIdField());
			if (root == null) {
				FieldNode node = new FieldNode(f);
				roots.add(node);
				setInput(roots);
				return node;
			} else {
				root.field = f;
				return root;
			}
		} else {
			String[] ids = f.getIdField().split("\\.");
			FieldNode root = getRoot(ids[0]);
			String id = ids[0];
			FieldNode parent = root;
			for (int i = 1; i < ids.length-1; i++) {
				id += "."+ids[i];
				parent = getChild(parent, id);
				if (parent == null) throw new FimetException("There not exist field "+id);
			}
			FieldNode child = getChild(parent, f.getIdField());
			if (child == null) {
				return parent.add(f);
			} else {
				child.field = f;
				return child;
			}
		}
	}
	private FieldNode getChild(FieldNode parent, String idField) {
		if (parent.hasChildren()) {
			for (FieldNode node : parent.getChildren()) {
				if (idField.equals(node.field.getIdField())) {
					return node;
				}
			}
		}
		return null;
	}
    public void select(String idField) {
		TreeItem item = getTreeItem(idField);
		if (item != null) {
			getTree().select(item);
		}
    }
    public TreeItem getTreeItem(String idField) {
		if (idField.indexOf('.') == -1) {
			return getRootItem(idField);
		} else {
			String[] ids = idField.split("\\.");
			TreeItem root = getRootItem(ids[0]);
			String id = ids[0];
			TreeItem parent = root;
			if (parent == null) return null;
			for (int i = 1; i < ids.length; i++) {
				id += "."+ids[i];
				parent = getChildItem(parent, id);
				if (parent == null) return null;
			}
			return parent;
		}
    }
	private TreeItem getRootItem(String idField) {
		for (TreeItem item : getTree().getItems()) {
			if (idField.equals(((FieldNode)item.getData()).getField().getIdField())) {
				return item;
			}
		}
		return null;
	}
	private TreeItem getChildItem(TreeItem parent, String idField) {
		if (parent.getItemCount() > 0) {
			for (TreeItem item : parent.getItems()) {
				if (idField.equals(((FieldNode)item.getData()).getField().getIdField())) {
					return item;
				}
			}
		}
		return null;
	}
}
