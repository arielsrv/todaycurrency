package com.todaycurrency.services;

import com.google.inject.Singleton;
import com.todaycurrency.common.Context;
import com.todaycurrency.model.CurrencyDto;
import io.reactivex.rxjava3.core.Observable;

@Singleton
public class CurrencyService {

	public Observable<CurrencyDto> getCurrency() {
		CurrencyDto currencyDto = new CurrencyDto();

		currencyDto.id = "USD";
		currencyDto.buy = "100.00";
		currencyDto.sell = "102.00";

		return Observable.just(currencyDto);
	}
}
