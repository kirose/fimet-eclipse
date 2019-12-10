package com.fimet.core.impl;

import java.util.List;

import com.fimet.core.IMessageIsoManager;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;
import com.fimet.persistence.sqlite.dao.MessageIsoDAO;

public class MessageIsoManager implements IMessageIsoManager {

	@Override
	public void free() {}

	@Override
	public void saveState() {}

	@Override
	public MessageIso insert(MessageIso m) {
		if (m.getId() == null) {
			m.setId(MessageIsoDAO.getInstance().getNextSequenceId());
		}
		MessageIsoDAO.getInstance().insert(m);
		return m;
	}

	@Override
	public MessageIso update(MessageIso m) {
		MessageIsoDAO.getInstance().update(m);
		return m;
	}

	@Override
	public MessageIso delete(MessageIso m) {
		MessageIsoDAO.getInstance().delete(m);
		return m;
	}

	@Override
	public List<MessageIso> find(MessageIsoParameters params) {
		return MessageIsoDAO.getInstance().findByParameters(params);
	}

}
