package com.fimet.core.impl.swt.msg;

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
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

import com.fimet.commons.DefaultStyler;
import com.fimet.commons.Images;
import com.fimet.commons.console.Console;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.Field;
import com.fimet.core.ISO8583.parser.IFieldParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.commons.Color;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FieldTree extends TreeViewer {

	public static final String ID = "com.fimet.preferences.FieldTreeViewer";
	public static final String CONTEXT_TREE = "com.fimet.context.FieldTree";
	private static IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
	private FieldNode header;
	private FieldNode mti;
	private List<FieldNode> roots = new ArrayList<>();
	private MessageViewer viewer;
	private Message message;
	
	static Styler stylerError;
    static Styler stylerKey;
    static Styler stylerName;
    static Styler stylerLength;
    static Styler stylerValue;
    IContextService contextService;
    IContextActivation contextActive;

	public FieldTree(MessageViewer viewer, Composite parent, boolean editable, int style) {
		super(parent, style);
		this.viewer = viewer;
		header = new FieldNode();
		header.idField = "header";
		header.name = "Header";
		mti = new FieldNode();
		mti.idField = "mti";
		mti.name = "Message Type Indicator";
		init();
		createContents(editable);
		//handleContext();
	}
	private IContextService getContextService() {
		return PlatformUI.getWorkbench() != null &&
				PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null ?
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(IContextService.class) : null;
	}
	private void handleContext() {
		contextService = getContextService();
		if (contextService != null) {
			//contextActive = contextService.activateContext(CONTEXT_TREE);
			getTree().addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					FieldNode field = getSelected();
					ISelectionProvider selectionProvider = viewer.getMessageContainer().getSelectionProvider();
					if (selectionProvider != null) {
						selectionProvider.setSelection(field != null ? new StructuredSelection(field) : null);
					}
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {}
			});
			getTree().addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
					if (contextActive != null) {
						contextService.deactivateContext(contextActive);
						//System.out.println("Deactive:"+contextService.getActiveContextIds());
						contextActive = null;
					}
				}
				@Override
				public void focusGained(FocusEvent e) {
					if (contextActive == null) {
						contextActive = contextService.activateContext(CONTEXT_TREE);
						//System.out.println("Active:"+contextService.getActiveContextIds());
					}
				}
			});
		}
	}
	private static void init() {
		if (stylerKey == null) {
			stylerError = new DefaultStyler(Color.RED, null);
		    stylerKey = new DefaultStyler(Color.PURPLE, null);
		    stylerName = new DefaultStyler(Color.CYAN, null);
		    stylerLength = new DefaultStyler(Color.GRAY2, null);
		    stylerValue = new DefaultStyler(Color.BLUE, null);
		}
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
		buildTree(message);
		expandAll();
	}
	public void createContents(boolean editable) {
		init();
        setContentProvider(new ViewContentProvider());
        setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider()));
        getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        createContextMenu(editable);
        if (editable) {
	        addDoubleClickListener(new IDoubleClickListener() {
	            @Override
	            public void doubleClick(DoubleClickEvent event) {
	            	viewer.onEditField();
	            }
	        });
        }
		//enableEditing(this);
	}
	private void buildTree(Message message) {
		roots = new ArrayList<>();
		roots.add(header);
		roots.add(mti);
		if (message != null) {
			header.value = message.getHeader();
			mti.value = message.getMti();
			Integer idGroup = message.getParser().getIdGroup();
			for (Field field : message.getRootFields()) {
				this.roots.add(new FieldNode(field, idGroup));
			}
		} else {
			header.value = "";
			mti.value = "";
		}
		setInput(roots);
	}
    
    public int remove(FieldNode parent, FieldNode node) {
    	if (node == header || node == mti) {
    		return -1;
    	}
    	message.remove(node.idField);
    	if (parent == null) {
    		int i = roots.indexOf(node);
        	roots.remove(node);
        	setInput(roots);
        	return i;
    	} else {
	    	int i = parent.remove(node);
	    	FieldTree.this.internalRefresh(parent);
	    	return i;
    	}
    }
    public void add(FieldNode node) {
    	FieldNode safe = getSafe(node.idField);
    	safe.value = node.value;
    	message.setValue(node.idField, node.value);
		//setInput(roots);
		if (safe.parent != null) {
			FieldTree.this.internalRefresh(safe.parent);
		}
		refresh();
		getTree().deselectAll();
		select(safe.idField);
    }
	public void update(FieldNode node) {
		if (header.idField.equals(node.idField)) {
			message.setHeader(node.value);
		} else if (mti.idField.equals(node.idField)) {
			message.setMti(node.value);
		} else {
	    	message.setValue(node.idField, node.value);
			if (node.hasChildren()) {
				try {
					IFieldParser fieldParser = fieldParserManager.getFieldParser(message.getParser().getIdGroup(), node.idField);
					ByteArrayReader reader = new ByteArrayReader(node.value.getBytes());
					fieldParser.parse(reader, message);
					internalRefresh(node);
					updateChildren(node);
				} catch (Exception e) {
					Console.getInstance().warning(FieldTree.class, "Error parsing field "+node.idField+", "+e.getMessage());
				}
			}
			if (node.parent != null) {
				internalRefresh(node.parent);
			}
		}
		refresh();
		getTree().deselectAll();
		select(node.idField);
	}
	private void updateChildren(FieldNode node) {
		if (node.hasChildren()) {
			for (FieldNode child : node.getChildren()) {
				child.value = message.getValue(child.idField);
				internalRefresh(child);
				if (child.hasChildren()) {
					updateChildren(child);
				}
			}
		}
	}
    public void select(String idField) {
		TreeItem item = getTreeItem(idField);
		if (item != null) {
			getTree().select(item);
		}
    }
    public FieldNode getSelected() {
    	IStructuredSelection selection = (IStructuredSelection) getSelection();
    	if (selection.getFirstElement() != null) {
    		return (FieldNode)selection.getFirstElement();
    	}
    	return null;
    }
    public List<FieldNode> getRoots(){
    	return roots;
    }
	public FieldNode getSafe(String idField) {
		if (idField == null || idField.length() == 0) {
			return null;
		}
		if (idField.indexOf('.') == -1) {
			return getRootSafe(idField);
		} else {
			String[] ids = idField.split("\\.");
			FieldNode root = getRootSafe(ids[0]);
			String id = ids[0];
			FieldNode parent = root;
			for (int i = 1; i < ids.length; i++) {
				id += "."+ids[i];
				parent = getSafe(parent, id);
			}
			return parent;
		}
	}
	private FieldNode getSafe(FieldNode parent, String idField) {
		if (parent.hasChildren()) {
			for (FieldNode node : parent.getChildren()) {
				if (idField.equals(node.idField)) {
					return node;
				}
			}
		}
		FieldNode node = new FieldNode(idField, null);
		node.assignGroup(message.getParser().getIdGroup());
		parent.add(node);
		return node;
	}
	private FieldNode getRootSafe(String idField) {
		for (FieldNode node : roots) {
			if (idField.equals(node.idField)) {
				return node;
			}
		}
		FieldNode node = new FieldNode(idField, null);
		node.assignGroup(message.getParser().getIdGroup());
		roots.add(node);
		setInput(roots);
		return node;
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
			if (idField.equals(((FieldNode)item.getData()).idField)) {
				return item;
			}
		}
		return null;
	}
	private TreeItem getChildItem(TreeItem parent, String idField) {
		if (parent.getItemCount() > 0) {
			for (TreeItem item : parent.getItems()) {
				if (idField.equals(((FieldNode)item.getData()).idField)) {
					return item;
				}
			}
		}
		return null;
	}
	/**
	 * Creates the context menu
	 * @param editable 
	 *
	 * @param viewer
	 */
	private void createContextMenu(boolean editable) {
	    MenuManager contextMenu = new MenuManager("#FieldTreeViewerMenu",ID); //$NON-NLS-1$
	    contextMenu.setRemoveAllWhenShown(true);
	    contextMenu.addMenuListener(new IMenuListener() {
	        @Override
	        public void menuAboutToShow(IMenuManager mgr) {
	            fillContextMenu(mgr, editable);
	        }
	    });
	    Menu menu = contextMenu.createContextMenu(this.getControl());
	    this.getControl().setMenu(menu);
	}
	
	/**
	 * Fill dynamic context menu
	 *
	 * @param contextMenu
	 * @param editable 
	 */
	private void fillContextMenu(IMenuManager contextMenu, boolean editable) {
	    contextMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
	    if (editable) {
		    contextMenu.add(new Action("New") {
		        @Override
		        public void run() {
		        	viewer.onNewField();
		        }
		    });
		    contextMenu.add(new Action("Edit") {
		        @Override
		        public void run() {
		        	viewer.onEditField();
		        }
		    });
		    contextMenu.add(new Action("Delete") {
		        @Override
		        public void run() {
		        	viewer.onDeleteField();
		        }
		    });
	    }
	    contextMenu.add(new Action("Copy") {
	        @Override
	        public void run() {
	        	FieldTree.this.onCopy();
	        }
	    });
	    if (editable) {
		    contextMenu.add(new Action("Paste") {
		        @Override
		        public void run() {
		        	viewer.onPasteFieldValue();
		        }
		    });
	    }
	    contextMenu.add(new Action("Expand All") {
	        @Override
	        public void run() {
	        	FieldTree.this.expandAll();
	        }
	    });
	    contextMenu.add(new Action("Collapse All") {
	        @Override
	        public void run() {
	        	FieldTree.this.collapseAll();
	        }
	    });
	}
	protected void onCopy() {
		FieldNode field = getSelected();
		if (field != null) {
			ViewUtils.setToClipboard(field.getValue());
		}
	}
	protected void onPaste() {
		FieldNode field = getSelected();
		String clipboard = ViewUtils.getFromClipboard();
		if (field != null && clipboard != null) {
			field.setValue(clipboard);
			update(field);
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
	private class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {

        private ImageDescriptor fieldParentImage;
        private ImageDescriptor fieldLeafImage;
        private ResourceManager resourceManager;

        public ViewLabelProvider() {
            this.fieldLeafImage = Images.FIELD_LEAF_ICON;
            this.fieldParentImage = Images.FIELD_PARENT_ICON;
        }

        @Override
        public StyledString getStyledText(Object element) {
        	return ((FieldNode)element).getStyledText();
        }

        @Override
        public Image getImage(Object element) {
            if(element instanceof FieldNode) {
                return getResourceManager().createImage(((FieldNode)element).hasChildren() ? fieldParentImage : fieldLeafImage);
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
	public void dispose() {
		if (contextActive != null) {
			contextService.deactivateContext(contextActive);
			//System.out.println("Deactive:"+contextService.getActiveContextIds());
			contextActive = null;
		}
	}
}
