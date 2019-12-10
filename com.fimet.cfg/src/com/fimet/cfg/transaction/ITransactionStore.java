package com.fimet.cfg.transaction;

import java.io.IOException;

import com.fimet.core.ISO8583.parser.Message;

public interface ITransactionStore {
	public boolean contains(ITransactionAnalyzer analyzer, Message message);
	public void save(ITransactionAnalyzer analyzer, Message message) throws IOException;
	public void close();
}
