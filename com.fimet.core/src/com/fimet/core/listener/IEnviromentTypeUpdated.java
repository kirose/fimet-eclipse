package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.EnviromentType;

public interface IEnviromentTypeUpdated extends IEnviromentListener {
	public void onEnviromentTypeUpdated(EnviromentType e);
}
