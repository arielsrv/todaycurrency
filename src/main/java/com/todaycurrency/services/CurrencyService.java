package com.todaycurrency.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.todaycurrency.clients.CurrencyClient;
import com.todaycurrency.model.CurrencyDto;
import io.reactivex.rxjava3.core.Observable;

@Singleton
public class CurrencyService {

	@Inject
	CurrencyClient currencyClient;

	public Observable<CurrencyDto> getCurrency() {
		return this.currencyClient.getLatest().map(currencyResponse -> {
			CurrencyDto currencyDto = new CurrencyDto();

			currencyDto.id = "USD";
			currencyDto.buy = currencyResponse.oficial.value_buy;
			currencyDto.sell = currencyResponse.oficial.value_sell;

			return currencyDto;
		});
	}
}
