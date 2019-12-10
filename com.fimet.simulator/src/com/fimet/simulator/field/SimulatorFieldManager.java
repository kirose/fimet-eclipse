package com.fimet.simulator.field;

import com.fimet.core.ISimulatorFieldManager;
import com.fimet.simulator.field.impl.*;

public class SimulatorFieldManager implements ISimulatorFieldManager {

	@Override
	public Class<?>[] getSimulatorClasses() {
		return new Class<?>[] {
			IfHasSetNewDateMMddhhmmss.class,
			IfHasSetNewDatehhmmss.class,
			IfHasSetNewDateMMdd.class,
			IfHasSetAmount.class,
			IfHasSetEntryMode.class,
			IfHasSetCorrectPanLastDigit.class,
			IfHasSetPanLast4Digits.class,
			IfHasSetRRN.class,
			SetRandom15N.class,
			SetRandom6N.class,
			SetRandom9N.class,
			SetNewDateMMddhhmmss.class,
			SetNewDatehhmmss.class,
			SetNewDateMMdd.class
		};
	}

	@Override
	public void free() {
	}

	@Override
	public void saveState() {
	}
}
