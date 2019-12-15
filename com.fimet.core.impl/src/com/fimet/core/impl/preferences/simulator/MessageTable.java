package com.fimet.core.impl.preferences.simulator;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.entity.sqlite.SimulatorMessage;
import com.fimet.core.entity.sqlite.pojo.SimulatorField;
import com.fimet.core.impl.swt.CrudTable;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

class MessageTable extends CrudTable<SimulatorMessage> {
	private SimulatorPage page;
	public MessageTable(SimulatorPage page, Composite parent) {
		super(parent, NO_BUTTONS | CONTEXT_MENU);
		this.page = page;
	}

	protected void createContents() {
        
		newColumn(50, "Header", new ColumnLabelProvider() {
            public String getText(Object element) {
        		return StringUtils.escapeNull(((SimulatorMessage)element).getHeader());
            }
        });
		newColumn(50, "Mti", new ColumnLabelProvider() {
            public String getText(Object element) {
            	return StringUtils.escapeNull(((SimulatorMessage)element).getMti());
            }
        });
		newColumn(140, "Require", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
	            return StringUtils.maxLength(StringUtils.join(((SimulatorMessage)element).getRequiredFields()),40);
            }
        });
		newColumn(140, "Exclude", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
	            return StringUtils.maxLength(StringUtils.join(((SimulatorMessage)element).getExcludeFields()),40);
            }
        });
		newColumn(140, "Include", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	List<SimulatorField> flds = ((SimulatorMessage)element).getIncludeFields();
            	if (flds != null && !flds.isEmpty()) {
            		StringBuilder s = new StringBuilder();
            		for (SimulatorField f : flds) {
						s.append(f.getIdField()).append(',');
					}
            		s.delete(s.length()-1, s.length());
            		return StringUtils.maxLength(s.toString(),40);
            	} else {
            		return "";
            	}
            }
        });
        
	}

	@Override
	protected void onNew() {
		page.onNewMessage();
	}

	@Override
	protected void onEdit() {
		page.onEditMessage();		
	}

	@Override
	protected void onDelete() {
		page.onDeleteMessage();		
	}
}
