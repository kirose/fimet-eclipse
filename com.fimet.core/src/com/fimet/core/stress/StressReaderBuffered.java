package com.fimet.core.stress;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import org.eclipse.core.resources.IResource;

import com.fimet.commons.exception.StressException;
import com.fimet.core.ISO8583.adapter.IStreamAdapter;

public class StressReaderBuffered implements IStressReader {
	private static final int BUFFER_SIZE = 10;
	private IResource resource;
	private StressReaderThread reader;
	private IStreamAdapter adapter;
	private RandomAccessFile randomFile;
	private FileInputStream stream;
	private byte[][] buffer = new byte[BUFFER_SIZE][];
	public StressReaderBuffered(IResource resource, IStreamAdapter adapter) {
		super();
		if (resource == null) {
			throw new StressException("Resource is null ");
		}
		this.adapter = adapter;
		this.resource = resource;
	}

	public void open() {
		try {
			randomFile = new RandomAccessFile(resource.getLocation().toFile(), "r");
			stream = new FileInputStream(resource.getLocation().toFile());
			adapter.readStream(stream);
			//stream.				
			//randomFile.read(buffer)
		} catch (FileNotFoundException e) {
			throw new StressException("Cannot open file "+resource.getLocation());
		}
	}

	public boolean hasNext() {
		return false;
	}

	public byte[] next() {
		return null;
	}

	public void close() {
		if (randomFile != null) {
			try {
				randomFile.close();
			} catch (Exception e) {}
		}
	}
	private void readBuffer() {
		
	}
}
