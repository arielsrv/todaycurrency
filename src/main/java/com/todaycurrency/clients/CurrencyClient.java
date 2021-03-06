package com.todaycurrency.clients;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.sdk.Client;
import com.sdk.Response;
import com.sdk.RestClient;
import io.reactivex.rxjava3.core.Observable;

@Singleton
public class CurrencyClient extends Client {

	@Inject
	public CurrencyClient(@Named("currency") RestClient restClient) {
		super(restClient, "currency");
	}

	public Observable<CurrencyResponse> getLatest() {

		String url = "https://api.bluelytics.com.ar/v2/latest";

		return this.restClient.get(url, CurrencyResponse.class)
			.map(Response::getData);
	}
}
