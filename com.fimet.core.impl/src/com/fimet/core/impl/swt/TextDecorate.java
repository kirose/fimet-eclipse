package com.fimet.core.impl.swt;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class TextDecorate extends VText {

	private ControlDecoration decorator;

	public TextDecorate(Composite parent, int style) {
		super(parent, style);
        decorator = new ControlDecoration(this, SWT.CENTER);
        decorator.setDescriptionText("Invalid field");
        Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();
        decorator.setImage(image);
        decorator.hide();
	}
    public boolean isValid() {
    	return isValid;
    }
    public void markValid() {
    	isValid = true;
    	decorator.hide();
    }
    public void markInvalid(String message) {
    	isValid = false;
    	decorator.setDescriptionText(message);
    	decorator.show();
    }
    public void setMarkDescription(String description) {
    	decorator.setDescriptionText(description);
    }
    public void markInvalid() {
    	isValid = false;
    	decorator.show();
    }
    public void mark(String message) {
    	if (isValid) {
    		markValid();
    	} else {
    		markInvalid(message);
    	}
    }
    public void mark() {
    	if (isValid) {
    		markValid();
    	} else {
    		markInvalid();
    	}
    }
    public void mark(boolean valid) {
    	if (valid) {
    		markValid();
    	} else {
    		markInvalid();
    	}
    }
    public void validateAndMark() {
    	if (validator != null) {
    		mark(validator.validate(this.getText()));
    	}
    }
    protected void checkSubclass () {}
    public static interface Validator{
    	boolean validate(String value);
    }
}
