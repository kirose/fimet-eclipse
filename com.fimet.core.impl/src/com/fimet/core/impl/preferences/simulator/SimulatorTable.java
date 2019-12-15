package com.fimet.core.impl.preferences.simulator;


import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.core.impl.swt.CrudTable;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

class SimulatorTable extends CrudTable<Simulator> {
	private SimulatorPage page;
	public SimulatorTable(SimulatorPage page, Composite parent) {
		super(parent, NO_BUTTONS | CONTEXT_MENU);
		this.page = page;
	}

	protected void createContents() {
		newColumn(30, "Id", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return ((Simulator)element).getId()+"";
            }
        });
		newColumn(70, "Type", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return ((Simulator)element).getType() == Simulator.ACQUIRER ? "Acquirer" : "Issuer";
            }
        });
		newColumn(160, "Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return StringUtils.escapeNull(((Simulator)element).getName());
            }
        });
	}

	@Override
	protected void onNew() {
		page.onNewSimulator();
	}

	@Override
	protected void onEdit() {
		page.onEditSimulator();
	}

	@Override
	protected void onDelete() {
		page.onDeleteSimulator();
	}

}
