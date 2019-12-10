package com.fimet.core.impl.preferences.enviroment;


import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.core.impl.swt.CrudTable;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

class TypeTable extends CrudTable<EnviromentType> {
	private EnviromentPage page;
	public TypeTable(EnviromentPage page, Composite parent) {
		super(parent, NO_BUTTONS | CONTEXT_MENU);
		this.page = page;
	}

	protected void createContents() {
		newColumn(30, "Id", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return ((EnviromentType)element).getId()+"";
            }
        });
		newColumn(160, "Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return StringUtils.escapeNull(((EnviromentType)element).getName());
            }
        });
	}

	@Override
	protected void onNew() {
		page.onNewType();
	}

	@Override
	protected void onEdit() {
		page.onEditType();
	}

	@Override
	protected void onDelete() {
		page.onDeleteType();
	}

}
