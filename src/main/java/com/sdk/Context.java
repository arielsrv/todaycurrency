package com.sdk;

import java.util.Map;

public class Context {
	public final Map<String, String> params;

	public Context(Map<String, String> params) {
		this.params = params;
	}
}
