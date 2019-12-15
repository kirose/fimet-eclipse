package com.fimet.core.impl.swt.msgiso;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.fimet.commons.Color;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.impl.swt.MessageIsoCombo;
import com.fimet.persistence.sqlite.dao.MessageIsoDAO;

public class MessageIsoFilteredCombo extends Composite {
	private Text txtMti;
	private Text txtDescription;
	private MessageIsoCombo cvMsg;
	private String name;
	public MessageIsoFilteredCombo(Composite parent, String name, int style) {
		super(parent, style);
		this.name = name;
	}
	
	public void createPartControl() {
		Label lbl;

		this.setLayout(new GridLayout(4,true));
		this.setFont(this.getFont());
		this.setBackground(Color.WHITE);
		
		lbl = new Label(this, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("MTI:");
		
		txtMti = new Text(this, SWT.BORDER);
		txtMti.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtMti.setBackground(Color.WHITE);
		
		lbl = new Label(this, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("Description:");
		
		txtDescription = new Text(this, SWT.BORDER);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtDescription.setBackground(Color.WHITE);
		
		lbl = new Label(this, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText(name+":");
		
		cvMsg = new MessageIsoCombo(this);
		cvMsg.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		hookListeners();
	}

	private void hookListeners() {
		ModifyListener findML = (ModifyEvent e) ->{
			if (!"".equals(txtMti.getText().trim()) || !"".equals(txtDescription.getText().trim())) {
				List<MessageIso> msgs = MessageIsoDAO.getInstance().findByMtiAndLikeName(txtMti.getText(), txtDescription.getText());
				cvMsg.setMessages(msgs);
			}
		};
		txtMti.addModifyListener(findML);
		txtDescription.addModifyListener(findML);
	}
	public MessageIso getMessageSelected() {
		return cvMsg.getMessageSelected();
	}
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		cvMsg.addSelectionChangedListener(listener);
	}
}
