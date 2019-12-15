package com.fimet.core;

import java.util.List;

import com.fimet.core.entity.sqlite.IRuleValue;

public interface ISocketFieldMapper {
	Object getBind(Integer id);
	List<IRuleValue> getValues();
}
