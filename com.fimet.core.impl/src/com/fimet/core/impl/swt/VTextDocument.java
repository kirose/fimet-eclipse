package com.fimet.core.impl.swt;

import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.widgets.Composite;


public class VTextDocument extends TextViewer{
	private Validator validator; 
	protected boolean isValid = true;

	public VTextDocument(Composite parent, int style) {
		super(parent, style);
	}
	public void valid(boolean valid) {
		isValid = valid;
	}
	public void valid() {
		isValid = true;
	}
	public void invalid() {
		isValid = false;
	}
	public boolean isValid() {
    	return isValid;
    }
    public void setValidator(Validator validator) {
    	this.validator = validator;
    }
    public void validate() {
    	if (validator != null) {
    		isValid = validator.validate(this.getDocument() != null ? this.getDocument().get() : null);
    	}
    }
    protected void checkSubclass () {}
    public static interface Validator{
    	boolean validate(String value);
    }

}
