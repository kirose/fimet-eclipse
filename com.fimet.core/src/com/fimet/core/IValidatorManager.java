package com.fimet.core;

import com.fimet.core.usecase.UseCase;

public interface IValidatorManager extends IManager {
	public IValidator validatorFor(UseCase useCase);
}
