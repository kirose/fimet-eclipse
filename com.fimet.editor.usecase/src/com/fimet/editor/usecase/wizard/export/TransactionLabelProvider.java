package com.fimet.editor.usecase.wizard.export;


import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Image;

import com.fimet.commons.Images;


public class TransactionLabelProvider extends org.eclipse.pde.internal.ui.util.SharedLabelProvider {
	private static TransactionLabelProvider instance;
	public static TransactionLabelProvider getInstance() {
		if (instance == null) {
			instance = new TransactionLabelProvider();
		}
		return instance;
	}
	private TransactionLabelProvider() {
		super();
	}
	@Override
	public String getText(Object obj) {
		if (obj instanceof IFile) {
			return ((IFile) obj).getFullPath().toString();
		}
		return super.getText(obj);
	}
	@Override
	public Image getImage(Object obj) {
		return Images.USE_CASE_ICON.createImage();
	}
}