package com.fimet.cfg.transaction;

import com.fimet.core.ISO8583.parser.Message;

public interface ITransactionAnalyzer {
	ITransactionAnalyzer analyze(Message message);
	String[] getPrimaryKey();
}
