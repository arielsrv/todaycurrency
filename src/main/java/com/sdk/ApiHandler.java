package com.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.ClassPath;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.reactivex.rxjava3.core.Observable;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import spark.Request;
import spark.Spark;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Singleton
public class ApiHandler {

	private final Logger logger = LogManager.getLogger(this.getClass());
	private final Set<Class<?>> controllers;
	private final Injector injector;
	private final ObjectMapper objectMapper;

	public ApiHandler() throws IOException {
		this.controllers = getControllers();
		this.injector = Guice.createInjector(new AppModule(this.controllers));
		this.objectMapper = this.injector.getInstance(ObjectMapper.class);
	}

	public Set<Class<?>> getControllers() throws IOException {
		Set<Class<?>> result = ClassPath.from(ClassLoader.getSystemClassLoader())
			.getTopLevelClasses("com.todaycurrency.controllers")
			.stream()
			.map(ClassPath.ClassInfo::load)
			.filter(clazz -> clazz.getSuperclass().getName().equals("com.sdk.ApiController"))
			.collect(Collectors.toSet());

		return result;
	}

	public void get(String path, String controller, String action) {
		this.logger.info("GET \t" + path);
		Spark.get(path, (request, response) -> getResult(controller, action, request));
	}

	public String getResult(String controller, String action, Request request) throws Exception {
		Object result = this.invoke(controller, action, request).blockingFirst();
		return objectMapper.writeValueAsString(result);
	}

	public Observable invoke(String controllerName, String actionName, Request request) throws Exception {

		Optional<Class<?>> apiController = getApiController(controllerName);
		Optional<Method> apiAction = getApiAction(actionName, apiController);
		Map<String, String> requestParams = getRequestParams(request);

		return (Observable) apiAction.get()
			.invoke(injector.getInstance(apiController.get()), new Context(requestParams));
	}

	private Map<String, String> getRequestParams(Request request) {

		Map<String, String> params = request.queryParams().stream()
			.collect(Collectors.toMap(param -> param.replace(":", ""), request::queryParams));

		request.params().keySet()
			.forEach(key -> params.put(key.replace(":", ""), request.params().get(key)));

		return params;
	}

	private Optional<Method> getApiAction(String actionName, Optional<Class<?>> apiController) throws Exception {
		Optional<Method> apiAction = stream(apiController.get().getDeclaredMethods())
			.filter(method -> actionName.equalsIgnoreCase(method.getName()))
			.findFirst();

		if (apiAction.isEmpty()) {
			throw new Exception();
		}
		return apiAction;
	}

	private Optional<Class<?>> getApiController(String controllerName) throws Exception {
		Optional<Class<?>> apiController = this.controllers.stream()
			.filter(controller -> controllerName.equalsIgnoreCase(controller.getSimpleName()))
			.findFirst();

		if (apiController.isEmpty()) {
			throw new Exception();
		}
		return apiController;
	}
}