package com.fimet.core.impl.preferences.socket;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.entity.sqlite.Socket;
import com.fimet.core.impl.swt.CrudTable;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

class SocketTable extends CrudTable<Socket> {
	private SocketPage page;
	public SocketTable(SocketPage page, Composite parent) {
		super(parent, BUTTONS_RIGTH | CONTEXT_MENU);
		this.page = page;
	}

	public void createContents() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gridData.heightHint = 300;
		getTable().getTable().setLayoutData(gridData);
		newColumn(30, "Id", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return ((Socket)element).getId()+"";
            }
        });
		newColumn(140, "Name", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return StringUtils.escapeNull(((Socket)element).getName());
			}
		});
		newColumn(80, "Address",new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.escapeNull(((Socket)element).getAddress());
            }
        });
		newColumn(50, "Port",new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return ((Socket)element).getPort()+"";
            }
        });
		newColumn(120, "Enviroment", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Socket)element).getEnviroment() != null ? StringUtils.escapeNull(((Socket)element).getEnviroment().getName()) : "";
			}
		});
		newColumn(70, "Adapter", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return element != null ? ((Socket)element).getAdapter().getName() : "";
            }
        });
		newColumn(80, "Parser", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return element != null ? ((Socket)element).getParser().getName() : "";
            }
        });
		newColumn(80, "Simulator", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return element != null ? ((Socket)element).getSimulator().getName() : "";
            }
        });
		newColumn(60, "Type", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
	            return ((Socket)element).isAcquirer() ? "Acquirer" : "Issuer";
            }
        });
	}

	@Override
	protected void onNew() {
		page.onNew();
	}

	@Override
	protected void onEdit() {
		page.onEdit();	
	}

	@Override
	protected void onDelete() {
		page.onDelete();
	}
	
}
