package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Enviroment;

public interface IEnviromentUpdated extends IEnviromentListener {
	public void onEnviromentUpdated(Enviroment e);
}
