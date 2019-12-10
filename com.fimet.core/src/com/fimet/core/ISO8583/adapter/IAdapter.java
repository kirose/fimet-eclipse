package com.fimet.core.ISO8583.adapter;

import com.fimet.core.entity.sqlite.IRuleValue;

public interface IAdapter extends IRuleValue {
	Integer getId();
	String getName();
}
