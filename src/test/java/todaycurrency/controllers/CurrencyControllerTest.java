package todaycurrency.controllers;

import com.todaycurrency.common.Context;
import com.todaycurrency.controllers.CurrencyController;
import com.todaycurrency.model.CurrencyDto;
import com.todaycurrency.services.CurrencyService;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyControllerTest {

	private CurrencyService currencyService;
	private CurrencyController currencyController;

	@BeforeEach
	public void setUp() {
		this.currencyService = mock(CurrencyService.class);
		this.currencyController = new CurrencyController();
		this.currencyController.currencyService = this.currencyService;
	}

	@Test
	public void getCurrency() {
		when(this.currencyService.getCurrency()).thenReturn(getCurrencyDto());

		CurrencyDto currencyDto = this.currencyController.getCurrency(new Context(new HashMap<>())).blockingFirst();

		assertNotNull(currencyDto);
		assertEquals("USD", currencyDto.id);
		assertEquals("100.00", currencyDto.buy);
		assertEquals("102.00", currencyDto.sell);
	}

	private Observable<CurrencyDto> getCurrencyDto() {
		CurrencyDto currencyDto = new CurrencyDto();

		currencyDto.id = "USD";
		currencyDto.buy = "100.00";
		currencyDto.sell = "102.00";

		return Observable.just(currencyDto);
	}
}
