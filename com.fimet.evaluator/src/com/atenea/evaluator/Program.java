package com.atenea.evaluator;

import java.util.ArrayList;
import java.util.List;

import com.atenea.evaluator.function.internal.Function;

public class Program {
	public List<Function> functions = new ArrayList<>();
	public void addFunction(Function function) {
		functions.add(function);
	}
}
