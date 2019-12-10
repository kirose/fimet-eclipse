package com.fimet.cfg.transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.exception.RawcomException;
import com.fimet.core.ISO8583.parser.Message;

public class TransactionStore implements ITransactionStore {

	private FileOutputStream out;
	private List<Integer> hashCodes;
	final int prime = 31;

	public TransactionStore(File out) {
		super();
		if (out == null) {
			throw new NullPointerException("File output is null");
		}
		try {
			this.out = new FileOutputStream(out);
		} catch (FileNotFoundException e) {
			throw new RawcomException(e);
		}
		hashCodes = new ArrayList<>();
	}
	@Override
	public boolean contains(ITransactionAnalyzer analyzer, Message message) {
		return hashCodes.contains(hashCode(analyzer, message));
	}
	private int hashCode(ITransactionAnalyzer analyzer, Message message) {
		String[] ids = analyzer.getPrimaryKey();
		int result = 1;
		for (String id : ids) {
			if ("mti".equals(id)) {
				result = prime * result + (message.getMti() == null ? 0 : message.getMti().hashCode());
			} else {
				result = prime * result + (message.hasField(id) ? message.getValue(id).hashCode() : 0);
			}
		}
		return result;
	}
	@Override
	public void save(ITransactionAnalyzer analyzer, Message message) throws IOException {
		if (!contains(analyzer, message)) {
			System.out.println("Persisting: "+analyzer + " - " + message.getMti());
			hashCodes.add(hashCode(analyzer, message));
			out.write(message.getParser().formatMessage(message));
			out.write((byte)10);
		}
	}
	@Override
	public void close() {
		try {
			if (out != null) {
				out.close();
			}
		} catch (Exception e) {}
	}
}
