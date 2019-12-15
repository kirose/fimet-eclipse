package com.fimet.simulator.field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.exception.FimetException;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.simulator.ISimulator;
import com.fimet.persistence.Activator;

class SimulatorMessage {
	protected ISimulator simulator;
	protected String header;
	protected String mti;
	protected List<String> requiredFields;
	protected List<String> excludeFields;
	protected List<SimulatorField> simulatedFields;
	public SimulatorMessage(ISimulator simulator, com.fimet.core.entity.sqlite.SimulatorMessage sm) {
		super();
		this.simulator = simulator;
		mti = sm.getMti();
		requiredFields = sm.getRequiredFields();
		excludeFields = sm.getExcludeFields();
		header = sm.getHeader();
		List<com.fimet.core.entity.sqlite.pojo.SimulatorField> incflds = sm.getIncludeFields();
		if (incflds != null && !incflds.isEmpty()) {
			simulatedFields = new ArrayList<>();
			for (com.fimet.core.entity.sqlite.pojo.SimulatorField sf : incflds) {
				SimulatorField simulatorField = null;
				switch (sf.getType()) {
				case com.fimet.core.entity.sqlite.pojo.SimulatorField.FIXED:
					simulatorField = new SimulatorFieldFixed(sf.getIdField(), sf.getValue());
					break;
				case com.fimet.core.entity.sqlite.pojo.SimulatorField.CUSTOM:
					ISimulatorField isf = null;
					String className = sf.getValue();
					if (sf.getValue() != null && sf.getValue().trim().length() > 0) {
						try {
							Method m = Class.forName(className).getMethod("getInstance");
							isf = (ISimulatorField)m.invoke(null);
							simulatorField = new SimulatorFieldCustom(sf.getIdField(), isf);
						} catch (NoSuchMethodException e) {
							Activator.getInstance().error("The class "+className + " must have the static method 'getInstance()'" , e);
						} catch (SecurityException e) {
							Activator.getInstance().error("The class "+className + " must have the static method 'getInstance()'" , e);
						} catch (IllegalAccessException e) {
							Activator.getInstance().error("The class "+className + " must have the static method 'getInstance()' as public" , e);
						} catch (IllegalArgumentException e) {
							Activator.getInstance().error("The class "+className + " must have the static method 'getInstance()' without arguments" , e);
						} catch (InvocationTargetException e) {
							Activator.getInstance().error("The class "+className + " must have the static method 'getInstance()'" , e);
						} catch (ClassNotFoundException e) {
							Activator.getInstance().error("Cannot found class "+className + "" , e);
						}
					}
					break;
				}
				if (simulatorField != null) {
					simulatedFields.add(simulatorField);
				} else {
					throw new FimetException("Not yet supported type '"+sf.getType()+"'");
				}
			}
		}
	}
	public boolean existsRequiredFields(Message msg, List<String> requiredFields) {
		if (requiredFields != null && !requiredFields.isEmpty()) {
			StringBuilder missingFields = new StringBuilder();
			for (String index : requiredFields) {
				if (msg.getField(index) == null) {
					missingFields.append(index).append(',');
				}
			}
			if (missingFields.length() != 0) {
				missingFields.delete(missingFields.length()-1, missingFields.length());
				Activator.getInstance().warning(simulator+" - The fields "+missingFields.toString()+" are required in message ("+msg.getMti()+"): \n\n"+msg.toJson());
				return false;
			}
		}
		return true;
	}
}
