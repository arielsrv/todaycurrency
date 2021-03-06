package todaycurrency.clients;

import com.sdk.Response;
import com.sdk.RestClient;
import com.todaycurrency.clients.CurrencyClient;
import com.todaycurrency.clients.CurrencyResponse;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyClientTest {

	private RestClient restClient;
	private CurrencyClient currencyClient;

	@BeforeEach
	public void setUp() {
		this.restClient = mock(RestClient.class);
		this.currencyClient = new CurrencyClient(this.restClient);
	}

	@Test
	public void get_currency() {
		when(this.restClient.get(eq("https://api.bluelytics.com.ar/v2/latest"), eq(CurrencyResponse.class)))
			.thenReturn(getCurrencyResponse());

		CurrencyResponse currencyResponse = this.currencyClient.getLatest().blockingFirst();

		assertNotNull(currencyResponse);
		assertNotNull(currencyResponse.oficial);
		assertEquals(100.0, currencyResponse.oficial.value_buy);
		assertEquals(100.0, currencyResponse.oficial.value_sell);
	}

	private Observable<Response<CurrencyResponse>> getCurrencyResponse() {

		Response<CurrencyResponse> response = new Response<>();
		response.data = new CurrencyResponse();
		response.data.oficial = new CurrencyResponse.Oficial();
		response.data.oficial.value_buy = 100.0;
		response.data.oficial.value_sell = 100.0;

		return Observable.just(response);
	}
}
