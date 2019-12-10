package com.fimet.cfg.view.messageiso;

import static com.fimet.core.entity.sqlite.pojo.MessageIsoType.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoType;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

public class MessageIsoTableViewer extends TableViewer {
	private List<MessageIso> messagesIso;
	public MessageIsoTableViewer(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	public void createContents() {

        Table table = this.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        this.setContentProvider(ArrayContentProvider.getInstance());
        
        TableViewerColumn col;

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(40);
        col.getColumn().setText("Id");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return ((MessageIso)element).getId() == null ? "" : (((MessageIso)element).getId()+"");
        		
            }
        });
        col.setEditingSupport(null);
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(400);
        col.getColumn().setText("Name");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.escapeNull(((MessageIso)element).getName());
        		
            }
        });
        col.setEditingSupport(new MessageIsoNameEditor());
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(150);
        col.getColumn().setText("Merchant");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.escapeNull(((MessageIso)element).getMerchant());
        		
            }
        });
        col.setEditingSupport(new MessageIsoMerchantEditor());

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(70);
        col.getColumn().setText("Mti");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return StringUtils.escapeNull(((MessageIso)element).getMti());
            }
        });
        col.setEditingSupport(new MessageIsoMtiEditor());

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(100);
        col.getColumn().setText("Processing code");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return StringUtils.escapeNull(((MessageIso)element).getProcessingCode());
            }
        });
        col.setEditingSupport(new MessageIsoProcessingCodeEditor());
        
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(150);
        col.getColumn().setText("Type");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return MessageIsoType.get(((MessageIso)element).getType()).toString();
            }
        });
        col.setEditingSupport(new MessageIsoTypeEditor());
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(70);
        col.getColumn().setText("Enviroment Type");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return Manager.get(IEnviromentManager.class).getEnviromentType(((MessageIso)element).getIdTypeEnviroment()).getName();
            }
        });
        col.setEditingSupport(null);
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(150);
        col.getColumn().setText("Message");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.maxLength(((MessageIso)element).getMessage(),20);
            }
        });
        col.setEditingSupport(null);
        
		setInput(messagesIso);
	}
	class MessageIsoTypeEditor extends EditingSupport {
	    private final ComboBoxViewerCellEditor editor;
	    private Object[] types = new Object[] {ACQ_REQ,ACQ_RES,ISS_REQ,ISS_RES}; 
	    public MessageIsoTypeEditor() {
	        super(MessageIsoTableViewer.this);
	        this.editor = new ComboBoxViewerCellEditor(MessageIsoTableViewer.this.getTable());
	        editor.setContentProvider(new IStructuredContentProvider() {
				@Override
				public Object[] getElements(Object inputElement) {
					return types;
				}
			});
	        editor.setLabelProvider(new LabelProvider() {
	        	@Override
	        	public String getText(Object element) {
	        		return super.getText(element);
	        	}
	        });
	    }
	    @Override
	    protected CellEditor getCellEditor(Object element) {
			editor.setInput(MessageIsoType.values());
	        Control control = editor.getControl();
	        if (control instanceof org.eclipse.swt.custom.CCombo) {
	        	org.eclipse.swt.custom.CCombo combo = (org.eclipse.swt.custom.CCombo) control;
	        	combo.setText(MessageIsoType.get(((MessageIso)element).getType()).toString());
	        }
	        return editor;
	    }
	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }
	    @Override
	    protected Object getValue(Object element) {
	        return ((MessageIso)element).getType();
	    }
	    @Override
	    protected void setValue(Object element, Object userInputValue) {
	    	if (userInputValue != null) {
	    		if (userInputValue instanceof MessageIsoType) {
	    			((MessageIso) element).setType(((MessageIsoType)userInputValue).getId());
	    		}
	    		MessageIsoTableViewer.this.update(element, null);
	    		MessageIsoTableViewer.this.refresh();
	    	}
	    }
	}
	class MessageIsoNameEditor extends EditingSupport {
	    private final CellEditor editor;
	    public MessageIsoNameEditor() {
	        super(MessageIsoTableViewer.this);
	        this.editor = new TextCellEditor(MessageIsoTableViewer.this.getTable());
	    }
	    @Override
	    protected CellEditor getCellEditor(Object element) {
	    	editor.setValue(((MessageIso)element).getName());
	        return editor;
	    }
	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }
	    @Override
	    protected Object getValue(Object element) {
	        return ((MessageIso) element).getName();
	    }
	    @Override
	    protected void setValue(Object element, Object userInputValue) {
	        ((MessageIso) element).setName(String.valueOf(userInputValue));
	        MessageIsoTableViewer.this.update(element, null);
	    	MessageIsoTableViewer.this.refresh();
	    }
	}
	class MessageIsoMerchantEditor extends EditingSupport {
	    private final CellEditor editor;
	    public MessageIsoMerchantEditor() {
	        super(MessageIsoTableViewer.this);
	        this.editor = new TextCellEditor(MessageIsoTableViewer.this.getTable());
	    }
	    @Override
	    protected CellEditor getCellEditor(Object element) {
	    	editor.setValue(((MessageIso)element).getMerchant());
	        return editor;
	    }
	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }
	    @Override
	    protected Object getValue(Object element) {
	        return ((MessageIso) element).getMerchant();
	    }
	    @Override
	    protected void setValue(Object element, Object userInputValue) {
	        ((MessageIso) element).setMerchant(String.valueOf(userInputValue));
	        MessageIsoTableViewer.this.update(element, null);
	    	MessageIsoTableViewer.this.refresh();
	    }
	}
	class MessageIsoMtiEditor extends EditingSupport {
	    private final CellEditor editor;
	    public MessageIsoMtiEditor() {
	        super(MessageIsoTableViewer.this);
	        this.editor = new TextCellEditor(MessageIsoTableViewer.this.getTable());
	    }
	    @Override
	    protected CellEditor getCellEditor(Object element) {
	    	editor.setValue(((MessageIso)element).getMti());
	        return editor;
	    }
	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }
	    @Override
	    protected Object getValue(Object element) {
	        return ((MessageIso) element).getMti();
	    }
	    @Override
	    protected void setValue(Object element, Object userInputValue) {
	        ((MessageIso) element).setMti(String.valueOf(userInputValue));
	        MessageIsoTableViewer.this.update(element, null);
	    	MessageIsoTableViewer.this.refresh();
	    }
	}
	class MessageIsoProcessingCodeEditor extends EditingSupport {
	    private final CellEditor editor;
	    public MessageIsoProcessingCodeEditor() {
	        super(MessageIsoTableViewer.this);
	        this.editor = new TextCellEditor(MessageIsoTableViewer.this.getTable());
	    }
	    @Override
	    protected CellEditor getCellEditor(Object element) {
	    	editor.setValue(((MessageIso)element).getProcessingCode());
	        return editor;
	    }
	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }
	    @Override
	    protected Object getValue(Object element) {
	        return ((MessageIso) element).getProcessingCode();
	    }
	    @Override
	    protected void setValue(Object element, Object userInputValue) {
	        ((MessageIso) element).setProcessingCode(String.valueOf(userInputValue));
	        MessageIsoTableViewer.this.update(element, null);
	    	MessageIsoTableViewer.this.refresh();
	    }
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
