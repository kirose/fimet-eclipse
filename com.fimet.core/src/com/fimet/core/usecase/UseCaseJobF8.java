package com.fimet.core.usecase;


import com.fimet.core.IFimetReport;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * Job especifico para ejecucion de F8, en este caso se debe de generar un reporte
 */
public class UseCaseJobF8 extends UseCaseJobAbstract {
	private IFimetReport fimetReport;
	public UseCaseJobF8(String name, UseCaseExecutorManager manager, IFimetReport fimetReport) {
		super(name,manager,fimetReport.getResources());
		this.fimetReport = fimetReport;
	}
	@Override
	public void onFinish() {
		super.onFinish();
		fimetReport.onFinishExecution();
	}
}
