package com.todaycurrency;

import com.sdk.ApiApplication;

public class Application extends ApiApplication {

	@Override
	public void routes() {
		get("/currency", "currencyController", "getCurrency");
	}
}

