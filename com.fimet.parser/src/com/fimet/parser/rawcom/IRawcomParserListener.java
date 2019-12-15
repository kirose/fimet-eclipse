package com.fimet.parser.rawcom;

import com.fimet.core.ISO8583.parser.Message;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public interface IRawcomParserListener {
	/**
	 * Fires when start reading rawcom file
	 */
	void onStart();
	/**
	 * Fires when finish read rawcom file
	 */
	void onFinish();
	/**
	 * Se invoca si se parsea correctamente el mensaje
	 * @param message El mensaje parseado
	 * @param bytes El arreglo de bytes del mensaje del rawcom
	 */
	void onParse(Message message, byte[] bytes);
	/**
	 * Se invoca si ocurre algun error al parsear el mensaje
	 * @param e
	 * @param bytes
	 */
	void onError(Exception e, byte[] bytes);
}
