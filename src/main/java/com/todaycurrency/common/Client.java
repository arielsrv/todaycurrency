package com.todaycurrency.common;

public class Client {

	protected final RestClient restClient;
	protected final String name;

	public Client(RestClient restClient, String name) {
		this.restClient = restClient;
		this.name = name;
	}
}
