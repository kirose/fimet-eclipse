package com.fimet.parser.rawcom;

import java.io.File;
import java.io.FileNotFoundException;

import com.fimet.core.ISO8583.adapter.IAdapterManager;
import com.fimet.core.ISO8583.adapter.IByteArrayAdapter;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public final class RawcomParser {

	private boolean stop;
	private IParser parser;
	private IRawcomParserListener listener;
	private RawcomReader reader;
	private File file;
	IByteArrayAdapter adapter = (IByteArrayAdapter)IAdapterManager.RAWCOM;
	public RawcomParser(File file, IParser parser, IRawcomParserListener listener) throws FileNotFoundException {
		if (file == null) {
			throw new NullPointerException("Rawcom file is null");
		}
		if (!file.exists()) {
			throw new FileNotFoundException("Rawcom file not exists: "+file.getName());
		}
		if (!file.isFile()) {
			throw new FileNotFoundException("Rawcom file is not a file:"+file.getName());
		}
		if (listener == null) {
			throw new NullPointerException("Rawcom listener is null");
		}
		if (parser == null) {
			throw new NullPointerException("Parser is null");
		}
		this.parser = parser;
		this.listener = listener;
		this.file = file;
	}
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	public void parse() throws FileNotFoundException {
		listener.onStart();
		this.stop = false;
		this.reader = new RawcomReader(file);
		Message message;
		for (Rawcom raw : reader) {
			if (stop) {
				break;
			}
			try {
				message = (Message)parser.parseMessage(adapter.readByteArray(raw.getBytes()));
				message.setAdapter(adapter);
				listener.onParse(message, raw.getBytes());
			} catch (Exception e) {
				listener.onError(e, raw.getBytes());
			}
		}
		listener.onFinish();
		reader.close();
	}
	/**
	 * This method will stop 
	 */
	public void stop() {
		stop = true;
	}
}
