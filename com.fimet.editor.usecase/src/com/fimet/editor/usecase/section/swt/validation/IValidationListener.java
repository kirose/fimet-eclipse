package com.fimet.editor.usecase.section.swt.validation;

import com.fimet.core.usecase.Validation;

public interface IValidationListener {
	void onAddValidation(Validation v);
	void onEditValidation(Validation v);
	void onDeleteValidation(Validation v);
	void onSwiftValidation(int i, int j);
}
