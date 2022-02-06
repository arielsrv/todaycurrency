package com.todaycurrency.clients;

public class CurrencyResponse {
	public Oficial oficial;

	public static class Oficial {
		public double value_buy;
		public double value_sell;
	}
}
