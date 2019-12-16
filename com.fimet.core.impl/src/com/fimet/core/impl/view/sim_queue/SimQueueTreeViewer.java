package com.fimet.core.impl.view.sim_queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.fimet.commons.utils.StringUtils;
import com.fimet.commons.Color;
import com.fimet.commons.DefaultStyler;
import com.fimet.commons.Images;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SimQueueTreeViewer extends TreeViewer {

	public static final String ID = "com.fimet.preferences.SimQueueTreeViewer";
	private static Styler NAME_COLOR = StyledString.createColorRegistryStyler(JFacePreferences.HYPERLINK_COLOR, null);
	private static Styler STYLER_PATH = new DefaultStyler(Color.GRAY2, null);
	private List<IResource> resources = new java.util.ArrayList<>(); 
	private SimQueueView viewer;

	public SimQueueTreeViewer(SimQueueView viewer, Composite parent, int style) {
		super(parent, style);
		this.viewer = viewer;
		createContents();
	}
	public void setIResources(List<IResource> resources) {
		if (resources != null && !resources.isEmpty()) {
			this.resources = resources;
			setInput(resources);
		} else {
			setInput(null);
		}
	}
	public void createContents() {
        setContentProvider(new ViewContentProvider());
        setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider()));
        getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        createContextMenu();
        setInput(resources);
        hookListeners();
	}
    private void hookListeners() {
    	addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				viewer.onEdit();
			}
		});
		/*Tree t = (Tree) getControl();
        t.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onEdit();
			}
        });*/
    }
	class ViewContentProvider implements ITreeContentProvider {
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }
        @Override
        public void dispose() {
        }
        @Override
        public Object[] getElements(Object inputElement) {
        	if (resources == null) {
        		return new IResource[0];
        	} else {
        		return resources.toArray(new IResource[resources.size()]);
        	}
        }
        @Override
        public Object[] getChildren(Object parentElement) {
            return null;
        }
        @Override
        public Object getParent(Object element) {
            return null;
        }
        @Override
        public boolean hasChildren(Object element) {
            return false;
        }
    }
	private class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {

        private ImageDescriptor simQueueImage;
        private ResourceManager resourceManager;

        public ViewLabelProvider() {
            this.simQueueImage = Images.TYPES_IMG;
        }

        @Override
        public StyledString getStyledText(Object element) {
        	IResource r = (IResource) element;
        	StyledString text = new StyledString(StringUtils.maxLength(r.getName(), 30), NAME_COLOR);
        	text.append(" ["+r.getProject().getName()+"/"+r.getProjectRelativePath().toString()+"]",STYLER_PATH);
        	return text;
		}

        @Override
        public Image getImage(Object element) {
            if(element instanceof IResource) {
                return getResourceManager().createImage(simQueueImage);
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
        contextMenu.add(new Action("Edit selection") {
            @Override
            public void run() {
            	viewer.onEdit();
            }
        });
        contextMenu.add(new Action("Remove") {
            @Override
            public void run() {
            	viewer.onRemove();
            }
        });
    }

    public int getSelectedIndex() {
    	IStructuredSelection selection = (IStructuredSelection) SimQueueTreeViewer.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return resources.indexOf((IResource)selection.getFirstElement());
    	}
    	return -1;
    }
    public TreeItem getTreeItem(IResource v) {
    	TreeItem[] items = getTree().getItems();
    	for (TreeItem i : items) {
			if (i.getData() == v) {
				return i;
			}
		}
    	return null;
    }
    public TreeItem getTreeItem(int i) {
    	return getTree().getItems()[i];
    }
    public List<IResource> getIResources(){
    	return resources;
    }
	public void doAdd(IResource v) {
		resources.add(v);
		setInput(resources);
		refresh();
		getTree().deselectAll();
		getTree().select(getTreeItem(v));
	}
	public void doRemove(IResource node) {
    	int i = getSelectedIndex();
		resources.remove(node);
    	refresh();
    	if (getTree().getItems() != null && getTree().getItems().length > 0) {
    		getTree().select(getTreeItem(i == 0 ? 0 : i-1));	
    	}
    	
	}
	public void doDown(int i) {

		if (i < resources.size()-1) {
			IResource v1 = resources.get(i);
			IResource v2 = resources.get(i+1);
			resources.set(i,v2);
			resources.set(i+1, v1);
			refresh();
			getTree().deselectAll();
			getTree().select(getTreeItem(v1));
		}
	}
	public void doUp(int i) {
		if (i > 0) {
			IResource v1 = resources.get(i);
			IResource v2 = resources.get(i-1);
			resources.set(i,v2);
			resources.set(i-1, v1);
			refresh();
			getTree().deselectAll();
			getTree().select(getTreeItem(v1));
		}
	}
	public List<IResource> getSelectedSimQueues() {
		IStructuredSelection selection = (IStructuredSelection) SimQueueTreeViewer.this.getSelection();
		Iterator<?> i = selection.iterator();
		List<IResource> s =new ArrayList<>();
		while (i.hasNext()) {
			Object next = i.next();
			s.add((IResource)next);
		}
		return s;
	}
}
