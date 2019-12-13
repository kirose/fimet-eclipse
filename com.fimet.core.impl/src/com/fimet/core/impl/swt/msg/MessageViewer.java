package com.fimet.core.impl.swt.msg;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IFieldParser;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;
import com.fimet.core.entity.sqlite.pojo.MessageIsoType;
import com.fimet.core.impl.Activator;
import com.fimet.core.impl.swt.msgiso.MessageIsoSearchDialog;
import com.fimet.core.net.ISocket;
import com.fimet.parser.util.ParserUtils;

public class MessageViewer extends Composite {

	FieldTree tree;
	TreeButtons buttons;
	IMessageContainer messageContainer;
	public MessageViewer(IMessageContainer section, boolean editable, Composite parent, int style) {
		super(parent, style);
		this.messageContainer = section;
		setBackground(parent.getBackground());
		createContents(this, editable);
	}
	private void createContents(Composite parent, boolean editable) {

		GridLayout layout = new GridLayout(editable ? 2 : 1,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gridData.heightHint = 300;
		parent.setLayout(layout);
		parent.setLayoutData(gridData);

		Composite cmpTree = new Composite(parent, SWT.NONE);
		layout = new GridLayout(1,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cmpTree.setLayout(layout);
		cmpTree.setLayoutData(gridData);
		cmpTree.setBackground(parent.getBackground());
		
		tree = new FieldTree(this, cmpTree, editable, SWT.SINGLE | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		if (editable) {
			gridData = new GridData(SWT.WRAP, SWT.FILL, false, true,1,1);
			gridData.heightHint = 300;
			buttons = new TreeButtons(this, parent, SWT.NONE);
			buttons.setLayoutData(gridData);
			hookListeners();
		}
	}
	private void hookListeners() {
	}
	public void onNewField() {
		if (tree.getMessage() == null) return;
		FieldDialog dialog = new FieldDialog(
			tree.getMessage().getParser().getIdGroup(),
			null,
			(tree.getSelected() != null ? tree.getSelected().idField+"." : null),
			getShell(),
			"New Field",
			SWT.NONE
		);
		dialog.open();
		FieldNode node = dialog.getField();
		if (node != null) {
			tree.add(node);
			messageContainer.onModifyMessage();
		}
	}
	public void onEditField() {
		if (tree.getSelected() == null || tree.getMessage() == null) {
			return;
		}
		FieldDialog dialog = new FieldDialog(
			tree.getMessage().getParser().getIdGroup(),
			tree.getSelected(),
			null,
			getShell(),
			"Edit Field",
			SWT.NONE
		);
		dialog.open();
		FieldNode node = dialog.getField();
		if (node != null) {
			tree.update(node);
			messageContainer.onModifyMessage();
		}
	}
	public void onDeleteField() {
		if (tree.getSelected() == null) {
			return;
		}
		FieldNode node = tree.getSelected();
		
		int i;
    	if (node.parent == null) {
    		i = tree.remove(node.parent, node);
			if (i-1 >= 0) {
				tree.select(tree.getRoots().get(i-1).idField);
			}
    	} else {
			i = tree.remove(node.parent, node);
			if (i-1 >= 0) {
				tree.select(node.parent.getChildren()[i-1].idField);
			}
    	}
    	if (i != -1) {
    		messageContainer.onModifyMessage();
    	}
	}
	public void onParse() {
		IParser parser = messageContainer.getConnection() != null ? messageContainer.getConnection().getParser() : null;
		MessageParseDialog dialog = new MessageParseDialog(getShell(), parser, SWT.NONE);
		dialog.open();
		Message message = dialog.getMessage();
		if (message != null) {
			setMessage(message);
			messageContainer.onModifyMessage();
		}
	}
	public void onSearch() {
		try {
			MessageIsoParameters params = new MessageIsoParameters();
			if (Manager.get(IEnviromentManager.class).getActive() != null) {
				params.setIdTypeEnviroment(Manager.get(IEnviromentManager.class).getActive().getIdType());
			}
			if (messageContainer.getConnection() != null) {
				ISocket socket = messageContainer.getConnection();
				if (socket != null && socket.getParser() != null) {
					params.setIdParser(socket.getParser().getId());
				}
			}
			params.setType(MessageIsoType.ACQ_REQ.getId());
			params.setAsc(Boolean.TRUE);
			MessageIsoSearchDialog dialog = new MessageIsoSearchDialog(params, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.NONE);
			dialog.open();
			if (dialog.getMessage() != null) {
				byte[] isoAndMli = Converter.hexToAscii(dialog.getMessage().getMessage().getBytes());
				Message msg = (Message)ParserUtils.parseMessage(isoAndMli, dialog.getMessage().getIdParser());
				setMessage(msg);
				messageContainer.onModifyMessage();
			}
		} catch (Exception e) {
			Activator.getInstance().error("Error searching message", e);
		}
	}
	public FieldTree getTree() {
		return tree;
	}
	public TreeButtons getButtons() {
		return buttons;
	}
	public void setMessage(Message message) {
		tree.setMessage(message);
	}
	public Message getMessage(){
		return tree.getMessage();
	}
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		tree.getTree().setEnabled(enabled);
		buttons.setEnabled(enabled);
	}
	public IMessageContainer getMessageContainer() {
		return messageContainer;
	}
	public void dispose() {
		tree.dispose();
	}
	public void onPasteFieldValue() {
		if (tree.getMessage() == null) return;
		FieldNode field = tree.getSelected();
		String value = ViewUtils.getFromClipboard();
		IFieldParser fieldParser = Manager.get(IFieldParserManager.class).getFieldParser(tree.getMessage().getParser().getIdGroup(), field.getIdField());
		if (fieldParser == null) {
			MessageDialog.openError(getShell(),"Invalid field","Unknow field id "+field.getIdField());
		} else if (!fieldParser.isValidValue(value)) {
			MessageDialog.openError(getShell(),"Invalid value","Invalid field value \""+value+"\" "+
					"expected format "+fieldParser.getType());
		} else if (!fieldParser.isValidLength(value)) {
			if (fieldParser.isFixed()) {
				MessageDialog.openError(getShell(),"Invalid length","Invalid field length ("+(value != null ? value.length() : 0)+") \""+value+"\" "+
						"expected length "+fieldParser.getLength());
			} else {
				MessageDialog.openError(getShell(),"Invalid length","Invalid field length ("+(value != null ? value.length() : 0)+") \""+value+"\" "+
						"expected max length "+((VarFieldParser)fieldParser).getMaxLength());				
			}
		} else {
			field.setValue(value);
			tree.update(field);
		}
		
	}
}
