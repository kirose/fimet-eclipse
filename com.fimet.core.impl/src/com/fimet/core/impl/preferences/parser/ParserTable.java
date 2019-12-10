package com.fimet.core.impl.preferences.parser;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IFieldFormatManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.entity.sqlite.Parser;
import com.fimet.core.impl.swt.CrudTable;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

class ParserTable extends CrudTable<Parser> {
	private ParserPage page;
	private IFieldFormatManager formatGroupManager;
	public ParserTable(ParserPage page, Composite parent) {
		super(parent, BUTTONS_RIGTH | CONTEXT_MENU);
		this.page = page;
		formatGroupManager = Manager.get(IFieldFormatManager.class);
	}

	public void createContents() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gridData.heightHint = 300;
		getTable().getTable().setLayoutData(gridData);
		newColumn(30, "Id", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return ((Parser)element).getId()+"";
            }
        });
		newColumn(140, "Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return StringUtils.escapeNull(((Parser)element).getName());
            }
        });
		newColumn(100, "Class",new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	String clazz = ((Parser)element).getParserClass();
            	if (clazz != null) {
            		return clazz.substring(clazz.lastIndexOf('.')+1);
            	}
            	return "";
            }
        });
		newColumn(90, "Converter",new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	Integer id = ((Parser)element).getIdConverter();
            	return id != null ? Converter.get(id).toString() : "";
        		
            }
        });
		newColumn(100, "Field Group", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
	            return formatGroupManager.getGroup(((Parser)element).getIdGroup()).getName();
            }
        });
		newColumn(80, "Type", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	Integer type = ((Parser)element).getType();
            	return type == IParser.ISO8583 ? "ISO8583" : "Layout";
            }
        });
		newColumn(80, "Key Sequence", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.escapeNull(((Parser)element).getKeySequence());
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
