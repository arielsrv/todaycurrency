package com.todaycurrency.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.todaycurrency.common.ApiController;
import com.todaycurrency.common.Context;
import com.todaycurrency.model.CurrencyDto;
import com.todaycurrency.services.CurrencyService;
import io.reactivex.rxjava3.core.Observable;

@Singleton
public class CurrencyController extends ApiController {

	@Inject
	public CurrencyService currencyService;

	public Observable<CurrencyDto> getCurrency(Context context) {
		return this.currencyService.getCurrency();
	}
}
