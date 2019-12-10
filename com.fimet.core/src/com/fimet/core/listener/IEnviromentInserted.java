package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Enviroment;

public interface IEnviromentInserted extends IEnviromentListener {
	public void onEnviromentInserted(Enviroment e);
}
