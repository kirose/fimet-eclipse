package com.fimet.core.impl.preferences.simulator;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Composite;
import com.fimet.commons.Color;
import com.fimet.core.entity.sqlite.pojo.SimulatorField;
import com.fimet.core.impl.swt.CrudTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class FieldTable extends CrudTable<SimulatorField> {
	private MessageDialog dialog;
	public FieldTable(MessageDialog dialog, Composite parent) {
		super(parent, NO_BUTTONS | CONTEXT_MENU);
		this.dialog = dialog;
	}

	public void createContents() {
        newColumn(100, "Id Field", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return ((SimulatorField)element).getIdField();
        		
            }
        });
        newColumn(80, "Type", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	char c = ((SimulatorField)element).getType();
            	switch(c) {
            	case SimulatorField.FIXED:
            		return "FIXED";
            	case SimulatorField.CUSTOM:
            		return "CUSTOM";
            	}
        		return "UNKNOW";
            }
        });
        newColumn(200, "Value", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return ((SimulatorField)element).getValue().substring(((SimulatorField)element).getValue().lastIndexOf('.')+1);
            }
        	@Override
        	public org.eclipse.swt.graphics.Color getBackground(Object element) {
        		return null;
        	}
        	@Override
        	public org.eclipse.swt.graphics.Color getForeground(Object element) {
        		SimulatorField sf = ((SimulatorField)element);
        		if (sf.getType() == SimulatorField.CUSTOM) {
        			try {
        				Class.forName(sf.getValue());
        				return Color.GREEN;
        			} catch (Exception e) {
        				return Color.RED;
        			}
        		} else {
        			return Color.BLUE;
        		}
        	}
        });
	}
	@Override
	protected void onNew() {
		dialog.onNewField();
	}
	@Override
	protected void onEdit() {
		dialog.onEditField();
	}
	@Override
	protected void onDelete() {
		dialog.onDeleteField();
	}
	protected void fillContextMenu(IMenuManager contextMenu) {
		super.fillContextMenu(contextMenu);
        contextMenu.add(new Action("Up") {
            public void run() {
            	FieldTable.this.up(getSelected());
            }
        });
        contextMenu.add(new Action("Down") {
            public void run() {
            	FieldTable.this.down(getSelected());
            }
        });
	}
}
