package com.fimet.core.usecase;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public interface UseCaseJobListener {
	/**
	 * Se invoca cuando inicia el task
	 * @param task
	 */
	void onStart(UseCaseExecutor task);
	/**
	 * Se ejecuta cuando se ha forzado la tarea a detenerse
	 * @param task
	 */
	void onStop(UseCaseExecutor task);
	/**
	 * Se ejecuta cuando se genera un timeout
	 * @param task
	 */
	void onTimeout(UseCaseExecutor task);
	/**
	 * Se invoca cuando el task completa su ciclo de vida, puede no ejecutarse
	 * @param task
	 */
	void onComplete(UseCaseExecutor task);
	/**
	 * se ejecuta cuando se termina el task, siempre se ejecuta
	 * @param task
	 */
	void onFinish(UseCaseExecutor task);
	/**
	 * Ejecuta la tarea de sin meterla en la queue de espera
	 * @param task
	 */
	void forceRunTask(UseCaseExecutor task);
}
