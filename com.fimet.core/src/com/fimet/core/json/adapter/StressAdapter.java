package com.fimet.core.json.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.stress.Stress;

public class StressAdapter extends TypeAdapter<Stress>{
	private final TypeAdapter<Stress> delegate;
	public StressAdapter(TypeAdapter<Stress> delegate) {
		if (delegate == null) {
			throw new NullPointerException("TypeAdapter delegate is null");
		}
		this.delegate = delegate;
	}
	@Override
	public Stress read(JsonReader in) throws IOException {
		Stress stress = delegate.read(in);
		/*if (stress.getAcquirers() != null) {
			List<StressAcquirer> acquirers = stress.getAcquirers();
			ISourceConnectionManager sourceConnectionManager = Manager.get(ISourceConnectionManager.class);
			ISourceConnection acquirer;
			for (StressAcquirer acq : acquirers) {
				acquirer = sourceConnectionManager.findSourceConnection(acq.getConnection());
				acq.setConnection(acquirer);
			}
		}*/
		return stress;
	}
	@Override
	public void write(JsonWriter out, Stress msg) throws IOException {
		delegate.write(out, msg);
	}
}
