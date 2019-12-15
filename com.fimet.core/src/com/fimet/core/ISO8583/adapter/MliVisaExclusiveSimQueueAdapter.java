package com.fimet.core.ISO8583.adapter;

public class MliVisaExclusiveSimQueueAdapter extends MliVisaSimQueueAdapter {

	MliVisaExclusiveSimQueueAdapter(int id, String name) {
		super(id, name, true);
	}
}
