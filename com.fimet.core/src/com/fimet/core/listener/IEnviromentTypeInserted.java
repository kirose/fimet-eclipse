package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.EnviromentType;

public interface IEnviromentTypeInserted extends IEnviromentListener {
	public void onEnviromentTypeInserted(EnviromentType e);
}
