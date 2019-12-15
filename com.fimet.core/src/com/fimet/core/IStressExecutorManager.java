package com.fimet.core;

import com.fimet.core.stress.Stress;

public interface IStressExecutorManager extends IManager {
	void execute(Stress stress);
}
