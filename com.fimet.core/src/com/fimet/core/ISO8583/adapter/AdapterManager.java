package com.fimet.core.ISO8583.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.exception.AdapterException;
import com.fimet.core.entity.sqlite.IRuleValue;

public class AdapterManager implements IAdapterManager {

	static final List<IAdapter> adapters = new ArrayList<>();
	static final List<IStreamAdapter> adaptersStream = new ArrayList<>();
	static final List<IStringAdapter> adaptersString = new ArrayList<>();
	static final List<IByteArrayAdapter> adaptersByteArray = new ArrayList<>();
	public AdapterManager() {
		IAdapterManager.NONE.toString();
	}
	public IAdapter getAdapter(String name) {
		for (IAdapter a : adapters) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}
	public IAdapter getAdapter(Integer id) {
		return adapters.get(id);
	}
	public List<IAdapter> getAdapters() {
		return adapters;
	}
	public List<IStreamAdapter> getStreamAdapters() {
		List<IStreamAdapter> list = new ArrayList<>(adaptersStream.size());
		for (IStreamAdapter a : adaptersStream) {
			list.add(a);
		}
		return list;
	}
	public List<IByteArrayAdapter> getByteArrayAdapters() {
		List<IByteArrayAdapter> list = new ArrayList<>(adaptersByteArray.size());
		for (IByteArrayAdapter a : adaptersByteArray) {
			list.add(a);
		}
		return list;
	}
	public List<IStringAdapter> getStringAdapters() {
		List<IStringAdapter> list = new ArrayList<>(adaptersString.size());
		for (IStringAdapter a : adaptersString) {
			list.add(a);
		}
		return list;
	}
	public IStringAdapter adapterFor(String message) {
		for(IStringAdapter a : adaptersString) {
			if (a.isAdaptable(message)) {
				return a;
			}
		}
		throw new AdapterException("Cannot determinate adapter for "+message);
	}
	public IByteArrayAdapter adapterFor(byte[] message) {
		for(IByteArrayAdapter a : adaptersByteArray) {
			if (a.isAdaptable(message)) {
				return a;
			}
		}
		throw new AdapterException("Cannot determinate adapter for "+new String(message));
	}
	
	
	@Override
	public void free() {
		
	}

	@Override
	public void saveState() {
		
	}

	@Override
	public Object getBind(Integer id) {
		return id != null ? adapters.get(id) : null;
	}
	@Override
	public List<IRuleValue> getValues() {
		List<IRuleValue> results = new ArrayList<>(adapters.size());
		for (IAdapter a : adapters) {
			results.add((IRuleValue)a);
		}
		return results;
	}

}
