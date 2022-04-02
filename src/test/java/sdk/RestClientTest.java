package sdk;

import com.sdk.Response;
import com.sdk.RestClient;
import com.todaycurrency.clients.CurrencyResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestClientTest {

	@Test
	public void simple_http_request_ok() {
		RestClient restClient = new RestClient();

		CurrencyResponse actual = restClient.get("https://api.bluelytics.com.ar/v2/latest", CurrencyResponse.class)
			.map(Response::getData)
			.blockingFirst();

		assertNotNull(actual);
	}

	@Test
	public void simple_http_request_error() {
		RestClient restClient = new RestClient();

		assertThrows(RuntimeException.class, () -> restClient.get("https://notfound.resource", CurrencyResponse.class)
			.map(Response::getData)
			.blockingFirst());
	}

	@Test
	public void simple_http_request_parsing() {
		RestClient restClient = new RestClient();

		assertThrows(RuntimeException.class, () -> restClient.get("https://api.bluelytics.com.ar/v2/latest", String.class)
			.map(Response::getData)
			.blockingFirst());
	}
}
