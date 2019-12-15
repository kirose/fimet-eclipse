package com.fimet.editor.usecase.wizard.export;

import java.io.File;

import org.eclipse.core.resources.IFile;

public class TransactionExportInfo {

	public boolean toDirectory;
	public boolean singleFile;
	public boolean zip;
	public String destinationDirectory;
	public String fileName;
	private TransactionModel[] transactions;
	
	public static class TransactionModel {
		private IFile iFile;
		private File srcFile;
		private File tgtFile;
		
		public IFile getIFile() {
			return iFile;
		}
		public void setIFile(IFile ifile) {
			this.iFile = ifile;
		}
		public File getSrcFile() {
			return srcFile;
		}
		public void setSrcFile(File srcFile) {
			this.srcFile = srcFile;
		}
		public File getTgtFile() {
			return tgtFile;
		}
		public void setTgtFile(File tgtFile) {
			this.tgtFile = tgtFile;
		}
		
	}

	public TransactionModel[] getFiles() {
		return transactions;
	}

	public void setIFiles(IFile[] trnFiles) {
		if (trnFiles == null) {
			transactions = null;
		} else {
			transactions = new TransactionModel[trnFiles.length];
			for (int i = 0; i < trnFiles.length; i++) {
				transactions[i] = new TransactionModel();
				transactions[i].iFile = trnFiles[i];
				transactions[i].srcFile = new File(trnFiles[i].getLocation().toString());
			}
		}
	}
	
}