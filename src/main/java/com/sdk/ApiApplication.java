package com.sdk;

import com.google.inject.Singleton;
import spark.servlet.SparkApplication;

import java.io.IOException;

@Singleton
public abstract class ApiApplication implements SparkApplication {

	protected ApiHandler apiHandler;

	@Override
	public void init() {
		try {
			this.apiHandler = new ApiHandler();
			this.routes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void routes();

	public void get(String path, String controller, String action) {
		this.apiHandler.get(path, controller, action);
	}
}
