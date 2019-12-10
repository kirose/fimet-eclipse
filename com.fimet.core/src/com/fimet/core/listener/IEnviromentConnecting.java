package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Enviroment;

public interface IEnviromentConnecting extends IEnviromentListener {
	public void onEnviromentConnecting(Enviroment e);
}
