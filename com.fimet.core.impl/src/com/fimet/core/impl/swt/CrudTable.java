package com.fimet.core.impl.swt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchActionConstants;

public abstract class CrudTable<E> extends Composite {
	private TableViewer table;
	public static int BUTTONS = 1;
	public static int CONTEXT_MENU = 2;
	public static int NO_BUTTONS = 0;
	public static int NO_CONTEXT_MENU = 0;
	public static int BUTTONS_BOTTOM = 4;
	public static int BUTTONS_RIGTH = 8;
	private List<E> entities = new ArrayList<>();
	public CrudTable(Composite parent, int tableStyle) {
		this(parent, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER, tableStyle);
	}
	public CrudTable(Composite parent) {
		this(parent, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER, BUTTONS_RIGTH | CONTEXT_MENU);
	}
	public CrudTable(Composite parent, int style, int tableStyle) {
		super(parent, SWT.NONE);
		setBackground(parent.getBackground());
		GridLayout layout;
		if ((BUTTONS_RIGTH & tableStyle) > 0 || (BUTTONS & tableStyle) > 0) {
			layout = new GridLayout(2,false);
		} else {
			layout = new GridLayout(1,true);
		}
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));
		
		table = new TableViewer(this, style);
		table.setContentProvider(ArrayContentProvider.getInstance());
        table.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        table.getTable().setHeaderVisible(true);
        table.getTable().setLinesVisible(true);
        if ((CONTEXT_MENU & tableStyle) > 0) {
        	createContextMenu();
        }
        if ((BUTTONS & tableStyle) > 0 || (BUTTONS_RIGTH & tableStyle) > 0 || (BUTTONS_BOTTOM & tableStyle) > 0) {
        	new CrudButtons(this, tableStyle);
        }
		createContents();
		table.setInput(entities);
		
    	table.addDoubleClickListener((DoubleClickEvent event)->{
			this.onEdit();
		});
	}
	protected void createContents() {
		
	}
    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#CrudTableViewerMenu"+UUID.randomUUID(),null);
        contextMenu.setRemoveAllWhenShown(true);
        contextMenu.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager mgr) {
                fillContextMenu(mgr);
            }
        });
        Menu menu = contextMenu.createContextMenu(table.getControl());
        table.getControl().setMenu(menu);
    }
    private void fillContextMenu(IMenuManager contextMenu) {
        contextMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        contextMenu.add(new Action("New") {
            public void run() {
            	onNew();
            }
        });
        contextMenu.add(new Action("Edit") {
            public void run() {
            	onEdit();
            }
        });
        contextMenu.add(new Action("Delete") {
            public void run() {
            	onDelete();
            }
        });
    }

	protected TableViewerColumn newColumn(int width, String name, ColumnLabelProvider lblProvider) {
		TableViewerColumn col = new TableViewerColumn(table, SWT.NONE);
        col.getColumn().setWidth(width);
        col.getColumn().setText(name);
        col.setLabelProvider(lblProvider);
        return col;
	}
	public void add(E e) {
		if (!entities.contains(e)) {
			entities.add(e);
			table.add(e);
		}
		table.refresh();
		table.getTable().deselectAll();
		table.getTable().select(entities.indexOf(e));
	}
	public void update(E e) {
		int i = entities.indexOf(e);
		if (i != -1) {
			set(i, e);
			table.refresh(e);
		}
	}
	public void delete(E selected) {
		E sm = getSelected();
		int i = entities.indexOf(sm);
		entities.remove(sm);
		if (i-1 >=0) {
			table.getTable().select(i-1);
		}
		table.refresh();
	}
	public void set(int i, E sm) {
		entities.set(i, sm);
		TableItem item = table.getTable().getItem(i);
		item.setData(sm);
	}
	@SuppressWarnings("unchecked")
	public E getSelected() {
		if (table.getSelection() != null) {
			IStructuredSelection sel = (IStructuredSelection)table.getSelection();
			return (E)sel.getFirstElement();
		}
		return null;
	}

	public void setInput(List<E> list) {
		entities = new ArrayList<>();
		for (E e : list) {
			entities.add(e);
		}
		table.setInput(entities = list);
	}

	public List<E> getEntities() {
		return entities;
	}
	public void addTableDoubleClickListener(IDoubleClickListener listener) {
		table.addDoubleClickListener(listener);
	}
	public void addTableSelectionListener (SelectionListener listener) {
		table.getTable().addSelectionListener(listener);
	}
	public TableViewer getTable() {
		return table;
	}
	class CrudButtons extends Composite {
		private Button btnNew;
		private Button btnEdit;
		private Button btnDelete;
		public CrudButtons(Composite parent, int style) {
			super(parent, SWT.NONE);
			Composite composite = this;
			setBackground(parent.getBackground());
			GridData gridData;
			if ((BUTTONS_RIGTH & style) > 0 || (BUTTONS & style) > 0) {
				GridLayout layout = new GridLayout(1,true);
				layout.marginWidth = 0;
				layout.marginHeight = 0;
				setLayout(layout);
				gridData = new GridData(SWT.FILL, SWT.TOP, false, false,1,1);
				gridData.widthHint = 80;
				setLayoutData(gridData);
				gridData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				gridData.widthHint = 75;
			} else {
				GridLayout layout = new GridLayout(4,false);
				layout.marginWidth = 0;
				layout.marginHeight = 0;
				setLayout(layout);
				setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1,1));
				gridData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				gridData.widthHint = 50;
				Label lbl = new Label(composite, SWT.NONE);
				lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1,1));
			}
			
			btnNew = new Button(composite, SWT.NONE);
			btnNew.setLayoutData(gridData);
			btnNew.setText("New");
			
			btnEdit = new Button(composite, SWT.NONE);
			btnEdit.setLayoutData(gridData);
			btnEdit.setText("Edit");
			
			btnDelete = new Button(composite, SWT.NONE);
			btnDelete.setLayoutData(gridData);
			btnDelete.setText("Delete");

			btnNew.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					onNew();
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {}
			});
			btnEdit.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					onEdit();
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {}
			});
			btnDelete.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					onDelete();
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {}
			});
		}
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			btnNew.setEnabled(enabled);
			btnEdit.setEnabled(enabled);
			btnDelete.setEnabled(enabled);
		}
	}
	abstract protected void onNew();
	abstract protected void onEdit();
	abstract protected void onDelete();
	public void permute(int i, int j) {
		if (i != j && i >= 0 && j >= 0 && i < getTable().getTable().getItemCount() && j < getTable().getTable().getItemCount()) {
			E r1 = entities.get(i);
			E r2 = entities.get(j);
			this.getEntities().set(i, r2);
			this.getEntities().set(j, r1);
			TableItem i1 = getTable().getTable().getItem(i);
			TableItem i2 = getTable().getTable().getItem(j);
			i1.setData(r2);
			i2.setData(r1);
			getTable().update(r1, null);
			getTable().update(r2, null);
			getTable().refresh();
		}
	}
}
