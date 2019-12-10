package com.fimet.core;

import java.util.List;

import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;

public interface IMessageIsoManager extends IManager {
	MessageIso insert(MessageIso m);
	MessageIso update(MessageIso m);
	MessageIso delete(MessageIso m);
	List<MessageIso> find(MessageIsoParameters params);
}
