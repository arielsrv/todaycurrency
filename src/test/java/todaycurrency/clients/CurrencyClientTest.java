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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyClientTest {

    private RestClient restClient;
    private CurrencyClient currencyClient;
    private Response response;

    @BeforeEach
    public void setUp() {
        this.restClient = mock(RestClient.class);
        this.currencyClient = new CurrencyClient(this.restClient);
        this.response = mock(Response.class);
    }

    @Test
    public void get_currency() {
        when(response.getData()).thenReturn(getResponse());
        when(this.restClient.get(any(), any())).thenReturn(Observable.just(response));

        CurrencyResponse currencyResponse = this.currencyClient.getLatest().blockingFirst();

        assertNotNull(currencyResponse);
        assertNotNull(currencyResponse.oficial);
        assertEquals(100.0, currencyResponse.oficial.value_buy);
        assertEquals(100.0, currencyResponse.oficial.value_sell);
    }

    private CurrencyResponse getResponse() {
        CurrencyResponse currencyResponse = new CurrencyResponse();

        currencyResponse.oficial = new CurrencyResponse.Oficial();
        currencyResponse.oficial.value_buy = 100.0;
        currencyResponse.oficial.value_sell = 100.0;

        return currencyResponse;
    }
}
