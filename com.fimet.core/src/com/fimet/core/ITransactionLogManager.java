package com.fimet.core;

import java.util.List;

import com.fimet.core.entity.sqlite.pojo.TransactionLog;
import com.fimet.core.entity.sqlite.pojo.TransactionParams;
import com.fimet.core.net.ISocket;

public interface ITransactionLogManager extends IManager {
	public List<TransactionLog> findTransactions(TransactionParams params);
	public String findLastTimestamp(ISocket connection);
	public TransactionLog findByTimestamp(String timestamp);
}
