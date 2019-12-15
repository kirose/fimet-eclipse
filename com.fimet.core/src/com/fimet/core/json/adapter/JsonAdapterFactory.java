package com.fimet.core.json.adapter;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.fimet.core.net.ISocket;
import com.fimet.core.stress.Stress;
import com.fimet.core.usecase.Field;
import com.fimet.core.usecase.UseCase;

import java.lang.reflect.Type;
import java.util.List;

public class JsonAdapterFactory implements TypeAdapterFactory {
	
	public static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.registerTypeAdapterFactory(new JsonAdapterFactory())
			.create();

	private static final Type useCaseType = new TypeToken<UseCase>() {}.getType();
	private static final Type stressType = new TypeToken<Stress>() {}.getType();
	private static final Type sourceConnectionInterfaceType = new TypeToken<ISocket>() {}.getType();
	private static final Type listFieldType = new TypeToken<List<Field>>() {}.getType();
	//private static final Type sourceConnectionType = new TypeToken<InterchangeAccessPoint>() {}.getType();
	@SuppressWarnings("unchecked")
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		if (type.getType().equals(listFieldType)) {
			return (TypeAdapter<T>)new ListFieldAdapter((TypeAdapter<UseCase>)gson.getDelegateAdapter(this, type));
		} else if (type.getType().equals(sourceConnectionInterfaceType)) { 
			return (TypeAdapter<T>)new SocketAdapter((TypeAdapter<UseCase>)gson.getDelegateAdapter(this, type));
		} else if (type.getType().equals(useCaseType)) {
			return (TypeAdapter<T>)new UseCaseAdapter((TypeAdapter<UseCase>)gson.getDelegateAdapter(this, type));
		} else if (type.getType().equals(stressType)) {
			return (TypeAdapter<T>)new StressAdapter((TypeAdapter<Stress>)gson.getDelegateAdapter(this, type));
		} else {
			return gson.getDelegateAdapter(this, type);
		}
	}
}
