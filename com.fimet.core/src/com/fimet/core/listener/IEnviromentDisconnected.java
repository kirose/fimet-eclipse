package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Enviroment;

public interface IEnviromentDisconnected extends IEnviromentListener {
	public void onEnviromentDisconnected(Enviroment e);
}