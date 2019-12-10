package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Enviroment;

public interface IEnviromentConnected extends IEnviromentListener {
	public void onEnviromentConnected(Enviroment e);
}
