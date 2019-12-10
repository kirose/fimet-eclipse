package com.fimet.cfg.transaction;

import com.fimet.core.ISO8583.parser.Message;

public interface ITransactionAnalyzerManager {
	ITransactionAnalyzer analyze(Message message);
}
