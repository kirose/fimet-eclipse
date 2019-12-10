package com.fimet.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import com.fimet.commons.Activator;


public final class FileUtils {
	private static final int SIZE_BUFFER = 512;
	private FileUtils() {
	}
	public static String readContents(File file) {
		if (file == null || !file.exists() || file.isDirectory())
			return null;
		InputStream in = null;
		StringBuilder content = new StringBuilder();
		try {
			in = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int i = 0;
			while ((i = in.read(bytes))>0) {
				content.append(new String(bytes,0,i));
			}
		} catch (IOException e) {
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}		
		return content.toString();
	}
	public static byte[] readBytesContents(File file) {
		if (file == null || !file.exists() || file.isDirectory())
			return null;
		if (file.length() > 0) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				byte[] bytes = new byte[(int)file.length()];
				in.read(bytes);
				return bytes;
			} catch (IOException e) {
				return null;
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {}
				}
			}
		} else {
			return new byte[0];
		}
	}
	public static void writeContents(File file, byte[] contents) {
		if (file == null)
			return;
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(contents);
		} catch (IOException e) {
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {}
			}
		}
	}
	public static void writeContents(File file, String contents) {
		if (file == null || file.isDirectory())
			return;
		java.io.OutputStreamWriter out = null;
		try {
			out = new java.io.FileWriter(file);
			out.write(contents == null ? "" : contents);
		} catch (IOException e) {
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {}
			}
		}
	}
	public static void createSubdirectories(File file) {
		String path = file.getAbsolutePath().replace('\\', '/');
		path = path.substring(0,path.lastIndexOf('/'));
		new File(path).mkdirs();
	}
	public static void write(File input, File output, long start, int length) {
		byte[] bytes = new byte[512];
		RandomAccessFile reader = null;
		FileOutputStream writer = null;
		try {
			writer = new FileOutputStream(output);
			reader = new RandomAccessFile(input,"r");
			reader.seek(start);
			int count = 0;
			int read;
			while (count < length && (read = reader.read(bytes, 0, Math.min(length-count, SIZE_BUFFER))) > 0) {
				writer.write(bytes,0,read);
				count += read;
			}
		} catch (IOException ex) {
			Activator.getInstance().warning("Cannot read file "+input.getName(), ex);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {}
			}
		}
	}
}
