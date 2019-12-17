package com.fimet.evaluator;

import java.util.ArrayList;
import java.util.List;

import com.fimet.evaluator.function.internal.Function;

public class Program {
	public List<Function> functions = new ArrayList<>();
	public void addFunction(Function function) {
		functions.add(function);
	}
}
