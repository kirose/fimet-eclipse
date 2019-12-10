package com.fimet.core.impl.swt;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.persistence.sqlite.dao.SimulatorDAO;

public class SimulatorCombo extends VCombo {

	private List<Simulator> simulators;
	private List<Simulator> acquirers;
	private List<Simulator> issuers;
	public SimulatorCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public SimulatorCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Simulator");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Simulator)element).getName()+" / 	"+(((Simulator)element).getType() == Simulator.ACQUIRER ? "Acquirer" : "Issuer");
			}
		});
		simulators = SimulatorDAO.getInstance().findAll();
		acquirers = new ArrayList<>();
		issuers = new ArrayList<>();
		if (simulators != null) {
			for (Simulator simulator : acquirers) {
				if (simulator.getType() == Simulator.ACQUIRER) {
					acquirers.add(simulator);
				} else {
					issuers.add(simulator);
				}
			}
		}
		setInput(simulators);
	}
	public Simulator getSelected() {
		if (getStructuredSelection() != null) {
			return (Simulator)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Integer id) {
		if (id != null && id >= 0) {
			int i = 0;
			for (Simulator e: simulators) {
				if (e.getId() == id) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		} else {
			getCombo().deselectAll();
		}
	}
	public void loadAcquirers() {
		setInput(acquirers);
	}
	public void loadIssuers() {
		setInput(issuers);
	}
}
