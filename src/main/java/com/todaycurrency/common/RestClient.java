package com.todaycurrency.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.concurrent.Future;

@Singleton
public class RestClient {

	final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
		.setSoTimeout(Timeout.ofSeconds(10))
		.build();
	final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
		.setIOReactorConfig(ioReactorConfig)
		.build();
	private final Logger logger = LogManager.getLogger(this.getClass());
	private final ObjectMapper objectMapper;

	public RestClient() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.objectMapper = objectMapper;
	}

	public <T> Observable<Response<T>> get(String url, Class<T> clazz) {

		try {
			client.start();
			SimpleHttpRequest request = new SimpleHttpRequest(Method.GET, new URI(url));
			final Future<SimpleHttpResponse> future = client.execute(
				SimpleRequestProducer.create(request),
				SimpleResponseConsumer.create(),
				new FutureCallback<SimpleHttpResponse>() {
					@Override
					public void completed(final SimpleHttpResponse response) {
						logger.info(request + " -> " + new StatusLine(response));
					}

					@Override
					public void failed(final Exception ex) {
						logger.error(request + " -> " + ex);
					}

					@Override
					public void cancelled() {
						logger.warn(request + " cancelled");
					}
				});

			return Observable.fromFuture(future)
				.map(simpleHttpResponse -> {
					T value = this.objectMapper.readValue(simpleHttpResponse.getBodyBytes(), clazz);
					Response<T> result = new Response<>();
					result.data = value;
					return result;
				})
				.subscribeOn(Schedulers.io())
				.onErrorResumeNext(this::mapError);
		} catch (Exception e) {
			return Observable.error(new RuntimeException(e));
		}
	}

	private <T> ObservableSource<? extends Response<T>> mapError(Throwable throwable) {
		Throwable t = throwable.getCause() == null ? throwable : throwable.getCause();
		return Observable.error(t);
	}
}
