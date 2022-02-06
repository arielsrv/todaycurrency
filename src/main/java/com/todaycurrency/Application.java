package com.todaycurrency;

import com.todaycurrency.common.ApiApplication;

public class Application extends ApiApplication {

	@Override
	public void routes() {
		get("/currency", "currencyController", "getCurrency");
	}
}

