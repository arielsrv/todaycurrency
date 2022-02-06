package com.todaycurrency;

import com.sdk.SparkWebServer;

import static java.lang.System.getProperty;

public class Program {

	public static void main(String[] args) throws Exception {

		String serverSystemPort = getProperty("server.port");
		int serverPort = serverSystemPort == null ? 8080 : Integer.parseInt(serverSystemPort);

		SparkWebServer
			.run(serverPort, Application.class);
	}
}

