package com.fimet.core.stress;

import java.util.Map;

import org.eclipse.core.resources.IResource;

import com.fimet.core.net.IMessenger;
import com.fimet.core.net.ISocket;

public class StressExecutor {
	private Stress stress;
	private IResource simQueuesResource;
	private Map<ISocket, IMessenger> connections;
	public StressExecutor(Stress stress) {
		super();
		this.stress = stress;
	}
	public Stress getStress() {
		return stress;
	}
	public void setStress(Stress stress) {
		this.stress = stress;
	}
	public Map<ISocket, IMessenger> getConnections() {
		return connections;
	}
	public void setConnections(Map<ISocket, IMessenger> connections) {
		this.connections = connections;
	}
	public IResource getSimQueuesResource() {
		return simQueuesResource;
	}
	public void setSimQueuesResource(IResource simQueuesResource) {
		this.simQueuesResource = simQueuesResource;
	}
	
}
