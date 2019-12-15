package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Enviroment;

public interface IEnviromentDeleted extends IEnviromentListener {
	public void onEnviromentDeleted(Enviroment e);
}
