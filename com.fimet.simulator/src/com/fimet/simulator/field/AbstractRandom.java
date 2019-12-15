package com.fimet.simulator.field;

import java.util.Random;

public class AbstractRandom {
	private Random random = new Random();
	protected String random(int digits) {
		if (digits > 0) {
			if (digits < 7) {
				return randomInt(digits);
			} else {
				return randomLong(digits);
			}
		} else {
			return "";
		}
	}
	protected String randomInt(int digits) {
		return String.format("%0"+digits+"d",random.nextInt(999999));
	}
	protected String randomLong(int digits) {
		String fmt = String.format("%0"+digits+"d", Math.abs((long)random.nextLong()));
		return fmt.length() > digits ? fmt.substring(0,digits) : fmt;
	}
}
