package sdk;

import com.sdk.ApiHandler;
import com.todaycurrency.controllers.CurrencyController;
import com.todaycurrency.model.CurrencyDto;
import org.junit.jupiter.api.Test;
import spark.Request;
import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ApiHandlerTest {

	@Test
	public void get_controllers() throws IOException {
		ApiHandler apiHandler = new ApiHandler();
		Set<Class<?>> actual = apiHandler.getControllers();
		assertNotNull(actual);
		assertTrue(actual.stream().anyMatch((value) -> value.isAssignableFrom(CurrencyController.class)));
	}

	@Test
	public void get_result() throws Exception {
		ApiHandler apiHandler = new ApiHandler();
		Object actual = apiHandler.invoke("currencyController", "getCurrency", mock(Request.class)).blockingFirst();
		assertNotNull(actual);
		assertInstanceOf(CurrencyDto.class, actual);
	}

	@Test
	public void get_invalid_result() throws Exception {
		ApiHandler apiHandler = new ApiHandler();
		assertThrows(Exception.class, () -> {
			apiHandler.invoke("anotherController", "getCurrency", mock(Request.class)).blockingFirst();
		});
	}
}
