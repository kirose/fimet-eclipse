package com.fimet.core.stress;

import java.util.List;

import org.eclipse.core.resources.IResource;

import com.fimet.core.net.ISocket;

public class Stress {
	
	public static final String CONNECTION = "connection";
	public static final String FILE = "file";
	private List<StressAcquirer> acquirers;
	private IResource resource;
	private String name;
	public Stress() {
		super();
	}
	public List<StressAcquirer> getAcquirers() {
		return acquirers;
	}
	public void setAcquirers(List<StressAcquirer> acquirers) {
		this.acquirers = acquirers;
	}
	public String toJson() {
		return null;
	}
	public IResource getResource() {
		return resource;
	}
	public void setResource(IResource resource) {
		this.resource = resource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
