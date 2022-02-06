package todaycurrency.services;

import com.todaycurrency.clients.CurrencyClient;
import com.todaycurrency.clients.CurrencyResponse;
import com.todaycurrency.model.CurrencyDto;
import com.todaycurrency.services.CurrencyService;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyServiceTest {

	private CurrencyClient currencyClient;
	private CurrencyService currencyService;

	@BeforeEach
	public void setUp() {
		this.currencyClient = mock(CurrencyClient.class);
		this.currencyService = new CurrencyService();
		this.currencyService.currencyClient = this.currencyClient;
	}

	@Test
	public void get_currency() {
		when(this.currencyClient.getLatest()).thenReturn(getCurrencyResponse());

		CurrencyDto currencyDto = this.currencyService.getCurrency().blockingFirst();

		assertNotNull(currencyDto);
		assertEquals(100.0, currencyDto.buy);
		assertEquals(100.0, currencyDto.sell);
	}

	private Observable<CurrencyResponse> getCurrencyResponse() {
		CurrencyResponse currencyResponse = new CurrencyResponse();

		currencyResponse.oficial = new CurrencyResponse.Oficial();
		currencyResponse.oficial.value_buy = 100.0;
		currencyResponse.oficial.value_sell = 100.0;

		return Observable.just(currencyResponse);
	}
}
