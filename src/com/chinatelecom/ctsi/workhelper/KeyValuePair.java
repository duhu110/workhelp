package com.chinatelecom.ctsi.workhelper;

public class KeyValuePair<T extends Object> {
	String key;
	T value;

	public KeyValuePair(String key, T value) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}