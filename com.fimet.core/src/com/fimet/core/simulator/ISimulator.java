package com.fimet.core.simulator;

import com.fimet.core.ISO8583.parser.Message;

public interface ISimulator {
	public Integer getId();
	public String getName();
	public Message simulate(Message message);
	public void free();
}
