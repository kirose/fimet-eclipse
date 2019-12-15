package com.fimet.core;

import java.util.List;

import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.core.listener.IEnviromentListener;

public interface IEnviromentManager extends IManager {
	public EnviromentType insertEnviromentType(EnviromentType e);
	public EnviromentType updateEnviromentType(EnviromentType e);
	public EnviromentType deleteEnviromentType(EnviromentType e);
	public Enviroment insert(Enviroment e);
	public Enviroment update(Enviroment e);
	public Enviroment delete(Enviroment e);
	public Enviroment getActive();
	public void start(Enviroment enviroment);
	public void stop(Enviroment enviroment);
	public EnviromentType getEnviromentType(Integer id);
	public List<EnviromentType> getEnviromentsTypes();
	public List<Enviroment> getEnviroments();
	public Integer getNextIdEnviromentType();
	public Integer getPrevIdEnviromentType();
	public List<Enviroment> getEnviromentsByIdType(Integer idType);
	public Integer getNextIdEnviroment();
	public Integer getPrevIdEnviroment();
	public Enviroment getEnviroment(Integer sqlArg);
	public void addFirstListener(int type, IEnviromentListener listener);
	public void addListener(int type, IEnviromentListener listener);
	public void removeListener(int type, IEnviromentListener listener);
}
