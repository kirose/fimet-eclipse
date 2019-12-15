package com.fimet.editor.usecase.section.swt.validation;

import java.util.List;

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
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.commons.Images;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.usecase.Validation;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class ValidationTreeViewer extends TreeViewer {

	public static final String ID = "com.fimet.preferences.ValidationTreeViewer";
	private static Styler NAME_COLOR = StyledString.createColorRegistryStyler(JFacePreferences.HYPERLINK_COLOR, null);
	private List<Validation> validations = new java.util.ArrayList<>(); 
	private ValidationViewer viewer;

	public ValidationTreeViewer(ValidationViewer viewer, Composite parent, int style) {
		super(parent, style);
		this.viewer = viewer;
	}
	public void setValidations(List<Validation> validations) {
		if (validations != null && !validations.isEmpty()) {
			this.validations = validations;
			setInput(validations);
		} else {
			this.validations = new java.util.ArrayList<>();
			setInput(validations);
		}
	}
	public void createContents(FormToolkit toolkit) {
        setContentProvider(new ViewContentProvider());
        setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider()));
        getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        createContextMenu();
        setInput(validations);
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
        	if (validations == null) {
        		return new Validation[0];
        	} else {
        		return validations.toArray(new Validation[validations.size()]);
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

        private ImageDescriptor validationImage;
        private ResourceManager resourceManager;

        public ViewLabelProvider() {
            this.validationImage = Images.VALIDATION_ICON;
        }

        @Override
        public StyledString getStyledText(Object element) {
        	StyledString styledString;
        	if (element instanceof Validation) {
        		Validation v = (Validation) element;
        		styledString = new StyledString(StringUtils.maxLength(v.getName(), 40), NAME_COLOR);
        		styledString.append(" ["+StringUtils.maxLength(v.getExpression(), 40)+"]",StyledString.createColorRegistryStyler(JFacePreferences.ACTIVE_HYPERLINK_COLOR, null));
        		styledString.append(" ["+StringUtils.maxLength(v.getExpected()+"", 40)+"]",StyledString.createColorRegistryStyler(JFacePreferences.ACTIVE_HYPERLINK_COLOR, null));
        	} else {
        		styledString = new StyledString(element != null ? element.toString() : "NULL", NAME_COLOR);
        	}
	        return styledString;
		}

        @Override
        public Image getImage(Object element) {
            if(element instanceof Validation) {
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
        contextMenu.add(new Action("Add Validation") {
            @Override
            public void run() {
            	viewer.onAdd();
            }
        });
        contextMenu.add(new Action("Delete") {
            @Override
            public void run() {
            	viewer.onDelete();
            }
        });
    }
    public Validation getSelected() {
    	IStructuredSelection selection = (IStructuredSelection) ValidationTreeViewer.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return (Validation)selection.getFirstElement();
    	}
    	return null;
    }
    public int getSelectedIndex() {
    	IStructuredSelection selection = (IStructuredSelection) ValidationTreeViewer.this.getSelection();
    	if (selection.getFirstElement() != null) {
    		return validations.indexOf((Validation)selection.getFirstElement());
    	}
    	return -1;
    }
    public TreeItem getTreeItem(Validation v) {
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
    public List<Validation> getValidations(){
    	return validations;
    }
	public void doAdd(Validation v) {
		validations.add(v);
		setInput(validations);
		refresh();
		getTree().deselectAll();
		getTree().select(getTreeItem(v));
	}
	public void doDelete(Validation node) {
    	int i = getSelectedIndex();
		validations.remove(node);
    	refresh();
    	if (getTree().getItems() != null && getTree().getItems().length > 0) {
    		getTree().select(getTreeItem(i == 0 ? 0 : i-1));	
    	}
    	
	}
	public void doDown(int i) {

		if (i < validations.size()-1) {
			Validation v1 = validations.get(i);
			Validation v2 = validations.get(i+1);
			validations.set(i,v2);
			validations.set(i+1, v1);
			refresh();
			getTree().deselectAll();
			getTree().select(getTreeItem(v1));
		}
	}
	public void doUp(int i) {
		if (i > 0) {
			Validation v1 = validations.get(i);
			Validation v2 = validations.get(i-1);
			validations.set(i,v2);
			validations.set(i-1, v1);
			refresh();
			getTree().deselectAll();
			getTree().select(getTreeItem(v1));
		}
	}
}
