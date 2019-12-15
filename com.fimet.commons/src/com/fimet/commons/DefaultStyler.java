package com.fimet.commons;

import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.TextStyle;

public class DefaultStyler extends Styler {
	private final Color fForegroundColor;
	private final Color fBackgroundColor;

	public DefaultStyler(Color foregroundColor,Color backgroundColor) {
		fForegroundColor = foregroundColor;
		fBackgroundColor = backgroundColor;
	}

	@Override
	public void applyStyles(TextStyle textStyle) {
		if (fForegroundColor != null) {
			textStyle.foreground = fForegroundColor;
		}
		if (fBackgroundColor != null) {
			textStyle.background =  fBackgroundColor;
		}
	}
}