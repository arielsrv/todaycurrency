package com.sdk;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.netflix.config.ConfigurationManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class AppModule extends AbstractModule {

	private static final Pattern REST_CLIENT_PATTERN = Pattern.compile("rest\\.client\\.([-_\\w]+)\\..*");

	private final Set<Class<?>> classes;

	public AppModule(Set<Class<?>> classes) {
		this.classes = classes;
	}

	@Override
	public void configure() {
		bindControllers();
		bindRestClients();
	}

	public List<String> getNamesInKeys(String prefix, Pattern pattern) {
		Set<String> names = new HashSet<>();
		ConfigurationManager.getConfigInstance()
			.getKeys(prefix)
			.forEachRemaining(key -> {
				String name = RegexHelper.getString(pattern.matcher(key), 1);
				names.add(name);
			});
		return new ArrayList<>(names);
	}

	private void bindRestClients() {
		List<String> poolNames = this.getNamesInKeys("rest.client", REST_CLIENT_PATTERN);
		poolNames.forEach(poolName -> {
			bind(RestClient.class)
				.annotatedWith(Names.named(poolName))
				.to(RestClient.class)
				.in(Scopes.SINGLETON);
		});
	}

	private void bindControllers() {
		this.classes.forEach(this::bind);
	}
}

