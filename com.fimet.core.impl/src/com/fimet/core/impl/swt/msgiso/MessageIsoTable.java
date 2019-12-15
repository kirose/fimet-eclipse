package com.fimet.core.impl.swt.msgiso;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.MessageIso;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

public class MessageIsoTable extends TableViewer {
	private List<MessageIso> messagesIso;
	private IParserManager parserManager;
	private IMessageIsoMonitor monitor;
	public MessageIsoTable(IMessageIsoMonitor monitor, boolean editable, Composite parent, int style) {
		super(parent, style);
		this.monitor = monitor;
		parserManager = Manager.get(IParserManager.class);
		createContents(editable);
		if (editable) {
			createContextMenu();
			addDoubleClickListener((DoubleClickEvent event)->{
				monitor.onEdit();
			});
		}
	}

	public void createContents(boolean editable) {

        Table table = this.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        this.setContentProvider(ArrayContentProvider.getInstance());
        
        TableViewerColumn col;
        if (editable) {
            col = new TableViewerColumn(this, SWT.NONE);
            col.getColumn().setWidth(70);
            col.getColumn().setText("Mti");
            col.setLabelProvider(new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
            		return StringUtils.escapeNull(((MessageIso)element).getMti());
                }
            });

            col = new TableViewerColumn(this, SWT.NONE);
            col.getColumn().setWidth(100);
            col.getColumn().setText("Processing code");
            col.setLabelProvider(new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
            		return StringUtils.escapeNull(((MessageIso)element).getProcessingCode());
                }
            });

//        	col = new TableViewerColumn(this, SWT.NONE);
//	        col.getColumn().setWidth(40);
//	        col.getColumn().setText("Id");
//	        col.setLabelProvider(new ColumnLabelProvider() {
//	            @Override
//	            public String getText(Object element) {
//	            	return ((MessageIso)element).getId() == null ? "" : (((MessageIso)element).getId()+"");
//	        		
//	            }
//	        });
        }
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(250);
        col.getColumn().setText("Name");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.escapeNull(((MessageIso)element).getName());
        		
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(150);
        col.getColumn().setText("Merchant");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.escapeNull(((MessageIso)element).getMerchant());
        		
            }
        });

        
        if (editable) {
//	        col = new TableViewerColumn(this, SWT.NONE);
//	        col.getColumn().setWidth(150);
//	        col.getColumn().setText("Type");
//	        col.setLabelProvider(new ColumnLabelProvider() {
//	            @Override
//	            public String getText(Object element) {
//	            	return MessageIsoType.get(((MessageIso)element).getType()).toString();
//	            }
//	        });

	        col = new TableViewerColumn(this, SWT.NONE);
	        col.getColumn().setWidth(100);
	        col.getColumn().setText("Parser");
	        col.setLabelProvider(new ColumnLabelProvider() {
	            @Override
	            public String getText(Object element) {
	            	return parserManager.getParser(((MessageIso)element).getIdParser()).getName();
	            }
	        });
        }
        
		setInput(messagesIso);
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
        Menu menu = contextMenu.createContextMenu(getControl());
        getControl().setMenu(menu);
    }
    private void fillContextMenu(IMenuManager contextMenu) {
        contextMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        contextMenu.add(new Action("Edit") {
            public void run() {
            	monitor.onEdit();
            }
        });
        contextMenu.add(new Action("Delete") {
            public void run() {
            	monitor.onDelete();
            }
        });
    }
	public MessageIso getSelected() {
		if (getSelection() != null) {
			IStructuredSelection sel = (IStructuredSelection)getSelection();
			return (MessageIso)sel.getFirstElement();
		}
		return null;
	}
	public void setMessagesIso(List<MessageIso> msgsIso) {
		messagesIso = msgsIso;
		setInput(messagesIso);
	}
	public List<MessageIso> getMessagesIso() {
		return messagesIso;
	}
	public MessageIso getSelectedMessage(){
		if (getStructuredSelection() != null) {
			return (MessageIso)getStructuredSelection().getFirstElement();
		}
		return null;
	}

	public List<MessageIso> getSelectedMessages(){
		if (getStructuredSelection() != null) {
			IStructuredSelection sel = getStructuredSelection();
			Iterator<?> i = sel.iterator();
			List<MessageIso> msgs = new ArrayList<>();
			while(i.hasNext()) {
				msgs.add((MessageIso)i.next());
			}
			return msgs;
		}
		return null;
	}
}
