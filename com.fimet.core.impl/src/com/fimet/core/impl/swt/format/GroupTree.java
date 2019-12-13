package com.fimet.core.impl.swt.format;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.fimet.commons.Images;
import com.fimet.core.entity.sqlite.FieldFormatGroup;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class GroupTree extends TreeViewer {

	public static final String ID = "com.fimet.preferences.FieldFormatGroupTreeViewer";
	private List<GroupNode> roots = new java.util.ArrayList<>(); 
	private IFieldController controller;

	public GroupTree(IFieldController controller, Composite parent, int style) {
		super(parent, style);
		this.controller = controller;
		createContents();
	}
	public void setFieldFormatGroups(List<FieldFormatGroup> roots) {
		this.roots = new java.util.ArrayList<>();
		if (roots != null && !roots.isEmpty()) {
			for (FieldFormatGroup group : roots) {
				this.roots.add(new GroupNode(group));
			}
		}
		setInput(this.roots);
	}
	public void createContents() {
        setContentProvider(new ViewContentProvider());
        setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider()));
        //getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        createContextMenu();
        setInput(roots);
	}
	class ViewContentProvider implements ITreeContentProvider {
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {}
        @Override
        public void dispose() {}
        @Override
        public Object[] getElements(Object inputElement) {
        	if (roots == null) {
        		return new GroupNode[0];
        	} else {
        		return roots.toArray(new GroupNode[roots.size()]);
        	}
        }
        @Override
        public Object[] getChildren(Object parent) {
            return ((GroupNode)parent).getChildren();
        }
        @Override
        public Object getParent(Object element) {
            return ((GroupNode)element).parent;
        }
        @Override
        public boolean hasChildren(Object element) {
            return ((GroupNode)element).hasChildren();
        }
    }
	private class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {

        private ImageDescriptor validationImage;
        private ResourceManager resourceManager;

        public ViewLabelProvider() {
            this.validationImage = Images.VALIDATION_ICON;
        }

        @Override
        public StyledString getStyledText(Object element) {
	        return ((GroupNode)element).getStyledText();
		}

        @Override
        public Image getImage(Object element) {
            if(element instanceof FieldFormatGroup) {
                return getResourceManager().createImage(validationImage);
            }
            return super.getImage(element);
        }

        @Override
        public void dispose() {
            // garbage collect system resources
            if(resourceManager != null) {
                resourceManager.dispose();
                resourceManager = null;
            }
        }

        protected ResourceManager getResourceManager() {
            if(resourceManager == null) {
                resourceManager = new LocalResourceManager(JFaceResources.getResources());
            }
            return resourceManager;
        }
    }
    /**
     * Creates the context menu
     *
     * @param viewer
     */
    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#FieldTreeViewerMenu",ID); //$NON-NLS-1$
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
        contextMenu.add(new Action("New Group") {
            @Override
            public void run() {
            	controller.onNewGroup();
            }
        });
        contextMenu.add(new Action("Edit Group") {
            @Override
            public void run() {
            	controller.onEditGroup();
            }
        });
        contextMenu.add(new Action("Delete Group") {
            @Override
            public void run() {
            	controller.onDeleteGroup();
            }
        });
    }
    public GroupNode getSelectedParentNode() {
    	IStructuredSelection selection = (IStructuredSelection) GroupTree.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return ((GroupNode)selection.getFirstElement()).parent;
    	}
    	return null;
    }
    public GroupNode getSelectedNode() {
    	IStructuredSelection selection = (IStructuredSelection) GroupTree.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return (GroupNode)selection.getFirstElement();
    	}
    	return null;
    }
    public FieldFormatGroup getSelected() {
    	IStructuredSelection selection = (IStructuredSelection) GroupTree.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return ((GroupNode)selection.getFirstElement()).group;
    	}
    	return null;
    }
    public FieldFormatGroup getSelectedParent() {
    	IStructuredSelection selection = (IStructuredSelection) GroupTree.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		GroupNode node = (GroupNode)selection.getFirstElement();
    		return node.parent != null ? node.parent.group : null;
    	}
    	return null;
    }
    public int getSelectedIndex() {
    	IStructuredSelection selection = (IStructuredSelection) GroupTree.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return roots.indexOf((GroupNode)selection.getFirstElement());
    	}
    	return -1;
    }
    public TreeItem getTreeItem(TreeItem[] items, GroupNode v) {
    	TreeItem item = null;
    	for (TreeItem i : items) {
			if (i.getData() == v) {
				return i;
			} else if (i.getItemCount() > 0) {
				internalRefresh(i);
				if ((item = getTreeItem(i.getItems(), v)) != null) return item;
			}
		}
    	return null;
    }
    public TreeItem getTreeItem(TreeItem[] items, Integer id) {
    	TreeItem item = null;
    	for (TreeItem i : items) {
			if (((GroupNode)i.getData()).getGroup().getId() == id) {
				return i;
			} else if (i.getItemCount() > 0 && (item = getTreeItem(i.getItems(), id)) != null){
				return item;
			}
		}
    	return null;
    }
    public TreeItem getTreeItem(int i) {
    	return getTree().getItems()[i];
    }
    public List<FieldFormatGroup> getFieldFormatGroups(){
    	List<FieldFormatGroup> roots = new ArrayList<>();
    	for (GroupNode node : this.roots) {
			roots.add(node.group);
		}
    	return roots;
    }
	public int indexOf(FieldFormatGroup group) {
		int i = 0;
		for (GroupNode node : roots) {
			if (node.group.equals(group)) {
				return i;
			}
			i++;
		}
		return -1;
	}
    public void add(GroupNode parent, FieldFormatGroup v) {
		GroupNode node;
		if (parent == null) {
			int index = indexOf(v);
			if (index == -1) {
				roots.add(node = new GroupNode(v));
			} else {
				node = roots.get(index);
			}
		} else {
			node = parent.add(v);
			internalExpand(node,true);
		}
		refresh();
		//expandAll();
		getTree().deselectAll();
		TreeItem item = getTreeItem(getTree().getItems(), node);
		if (item != null)
			getTree().select(item);
	}
	public void delete(GroupNode parent, GroupNode group) {
		if (parent == null) {
			int index = roots.indexOf(group);
			if (index != -1) {
				roots.remove(index);
				setInput(roots);
			}
		} else {
			parent.remove(group);
		}
    	refresh();
	}
	public void update(GroupNode parent, FieldFormatGroup group) {
		refresh();
	}
	public GroupNode getNode(Integer idParent) {
		if (idParent == null)return null;
		TreeItem item = getTreeItem(getTree().getItems(), idParent);
		if (item == null) return null;
		return (GroupNode)item.getData();
	}
}
