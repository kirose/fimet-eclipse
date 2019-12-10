package com.fimet.parser.util;


import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;

import com.fimet.commons.exception.AdapterException;
import com.google.gson.reflect.TypeToken;
import com.fimet.commons.io.FileUtils;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.IAdapterManager;
import com.fimet.core.ISO8583.adapter.IByteArrayAdapter;
import com.fimet.core.ISO8583.adapter.IStringAdapter;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.stress.Stress;
import static com.fimet.core.json.adapter.JsonAdapterFactory.GSON;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class ParserUtils {

	public static final String REGEXP_HEX_STAR = "[0-9A-Fa-f*]+";
	public static final String REGEXP_HEX = "[0-9A-Fa-f]+";
	public static final String REGEXP_LOG = "[0-9A-Fa-f\n\t\r ]+";
	public static final String REGEXP_JSON = "(?s)^\\s*\\{\\s*\\\"[a-zA-Z]+.*";
	public static final String REGEXP_RAWCOM                   = "\\[T: [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}\\]\\[D:[0-9A-Za-z ]+\\]\\[C:[0-9A-Za-z.* ]+\\]\\[Iap:[a-zA-Z0-9_\\- ]+\\]\\[Lp:[0-9:a-zA-Z-_.* ]+\\]\\[Rw:[0-9a-zA-Z ]+\\]\\[L:[ ]+[0-9]+](?s).+";
	public static final Pattern PATTERN_RAWCOM = Pattern.compile("\\[T: [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}\\]\\[D:[0-9A-Za-z ]+\\]\\[C:[0-9A-Za-z.* ]+\\]\\[Iap:[a-zA-Z0-9_\\- ]+\\]\\[Lp:[0-9:a-zA-Z-_.* ]+\\]\\[Rw:[0-9a-zA-Z ]+\\]\\[L:[ ]+[0-9]+](.+)",Pattern.DOTALL);	
	public static final Pattern PATTERN_LOG_MSG = Pattern.compile("([\t ]*[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[\t ]+[0-9A-Fa-f]{2}[ \n\r\t]*)");
	private static IAdapterManager adapterManager = Manager.get(IAdapterManager.class);
	private static IParserManager parserManager = Manager.get(IParserManager.class);
	
	public static Message parseJsonMessage(String json) {
		//gson.toJson(gson.fromJson(json, Message.class))
		return GSON.fromJson(json, Message.class);
	}
	public static byte[] formatMessage(Message msg) {
		if (msg.getAdapter() instanceof IByteArrayAdapter) {
			return ((IByteArrayAdapter)msg.getAdapter()).writeByteArray(msg.getParser().formatMessage(msg));
		} else {
			throw new AdapterException(msg.getAdapter()+" must implement IByteArrayAdapter");
		}
	}
	public static IMessage parseMessage(byte[] bytes, int idParser) {
		return parseMessage(bytes, parserManager.getParser(idParser));
	}
	public static IMessage parseMessage(String message, int idParser) {
		if (message.matches(REGEXP_JSON)) {
			return parseJsonMessage(message);
		}
		return parseMessage(message, parserManager.getParser(idParser));
	}
	public static IMessage parseMessage(String message, String parserName) {
		return parseMessage(message, parserManager.getParser(parserName));
	}
	public static IMessage parseMessage(byte[] message, String parserName) {
		return parseMessage(message, parserManager.getParser(parserName));
	}
	public static IMessage parseMessage(byte[] message, IParser parser) {
		IByteArrayAdapter adapter = adapterManager.adapterFor(message);
		IMessage parseMessage = parser.parseMessage(adapter.readByteArray(message));
		parseMessage.setAdapter(adapter);
		return parseMessage;
	}
	/**
	 * Parse a sim_queue (Hexadecimal) to a Msg
	 * @param msgHex
	 * @param parserName
	 * @return
	 */
	public static IMessage parseMessage(String message, IParser parser) {
		IStringAdapter adapter = adapterManager.adapterFor(message);
		IMessage parseMessage = parser.parseMessage(adapter.readString(message));
		parseMessage.setAdapter(adapter);
		return parseMessage;
	}
	public static Stress parseStress(String name, String json) {
		Stress stress = GSON.fromJson(json, Stress.class);
		stress.setName(name);
		return stress;
	}
	public static Stress parseStress(IResource resource) {
		File file = new File(resource.getLocation().toString());
		String json = FileUtils.readContents(file);
		Stress stress = GSON.fromJson(json, Stress.class);
		stress.setResource(resource);
		stress.setName(file.getName().substring(0,file.getName().lastIndexOf('.')));
		return stress;
	}
	public static Map<String,Object> parseJsonAsMap(String json){
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		return GSON.fromJson(json, type);
	}
	public static Object parseJson(String json, Type type){
		return GSON.fromJson(json, type);
	}
}
