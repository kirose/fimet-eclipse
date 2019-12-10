package com.fimet.core.impl.swt;

import org.eclipse.swt.widgets.Composite;

public class VText extends org.eclipse.swt.widgets.Text {

	protected Validator validator; 
	protected boolean isValid = true;

	public VText(Composite parent, int style) {
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
    		isValid = validator.validate(this.getText());
    	}
    }
    protected void checkSubclass () {}
    public static interface Validator{
    	boolean validate(String value);
    }
}
