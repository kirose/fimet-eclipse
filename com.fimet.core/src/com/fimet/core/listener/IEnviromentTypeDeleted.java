package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.EnviromentType;

public interface IEnviromentTypeDeleted extends IEnviromentListener {
	public void onEnviromentTypeDeleted(EnviromentType e);
}
