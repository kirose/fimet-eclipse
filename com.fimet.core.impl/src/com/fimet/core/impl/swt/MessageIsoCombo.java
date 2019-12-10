package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.entity.sqlite.MessageIso;

public class MessageIsoCombo extends VCombo {

	private List<MessageIso> messages;  
	public MessageIsoCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public MessageIsoCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Message");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
		    public String getText(Object element) {
		        if (element instanceof MessageIso) {
		        	MessageIso msg = (MessageIso) element;
		            return StringUtils.maxLength(msg.getMti() + "/" +msg.getName(),40);
		        }
		        return super.getText(element);
		    }
		});
	}
	public void setMessages(List<MessageIso> msgs) {
		this.messages = msgs;
		setInput(msgs);
	}
	public MessageIso getMessageSelected() {
		if (getStructuredSelection() != null) {
			return (MessageIso)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(MessageIso select) {
		if (select != null && messages != null) {
			int i = 0;
			for (MessageIso iap : messages) {
				if (iap.equals(select)) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		}
	}
}
