package com.todaycurrency.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.newrelic.api.agent.Trace;
import com.sdk.ApiController;
import com.sdk.Context;
import com.todaycurrency.model.CurrencyDto;
import com.todaycurrency.services.CurrencyService;
import io.reactivex.rxjava3.core.Observable;

@Singleton
public class CurrencyController extends ApiController {

	@Inject
	public CurrencyService currencyService;

	@Trace(dispatcher = true, metricName = "GET /currency")
	public Observable<CurrencyDto> getCurrency(Context context) {
		return this.currencyService.getCurrency();
	}
}
