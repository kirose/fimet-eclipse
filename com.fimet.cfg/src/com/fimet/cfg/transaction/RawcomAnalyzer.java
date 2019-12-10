package com.fimet.cfg.transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.fimet.cfg.Activator;
import com.fimet.commons.console.Console;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.parser.rawcom.Rawcom;
import com.fimet.parser.rawcom.RawcomReader;

public class RawcomAnalyzer {
	private ITransactionAnalyzerManager transactionAnalyzerManager;
	private File in;
	private IParser parser;
	private AtomicBoolean stop = new AtomicBoolean(false);
	private ITransactionStore store;
	
	public RawcomAnalyzer(File in, File out, IParser parser, ITransactionAnalyzerManager transactionAnalyzerManager) {
		if (in == null) {
			throw new NullPointerException("Input file is null");
		}
		if (out == null) {
			throw new NullPointerException("Output file is null");
		}
		if (parser == null) {
			throw new NullPointerException("Parser is null");
		}
		if (transactionAnalyzerManager == null) {
			throw new NullPointerException("TransactionAnalyzerManager is null");
		}
		this.in = in;
		this.parser = parser;
		this.transactionAnalyzerManager = transactionAnalyzerManager;
		this.store = new TransactionStore(out);
	}
	public void analyze() {
		FileInputStream input = null;
		stop.set(false);
		try {
			input = new FileInputStream(in);
			Message message;
			RawcomReader reader = new RawcomReader(in);
			ITransactionAnalyzer match;
			for (Rawcom raw : reader) {
 				if (stop.get()) {
					Console.getInstance().debug(RawcomAnalyzer.class, "Stoped!!");
					break;
				}
				message = (Message)parser.parseMessage(raw.getMessage());
				match = transactionAnalyzerManager.analyze(message);
				if (match != null) {
					if (!store.contains(match, message)) {
						store.save(match, message);
					}
				}
			}
			store.close();
		} catch (IOException e) {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ex) {}
			}
			Activator.getInstance().error("Rawcom Analyzer", e);
		}
	}
	public void stop() {
		stop.set(false);
	}
}
