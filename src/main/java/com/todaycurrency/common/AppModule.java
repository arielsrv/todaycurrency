package com.todaycurrency.common;

import com.google.inject.AbstractModule;

import java.util.Set;

public class AppModule extends AbstractModule {

	private final Set<Class<?>> classes;

	public AppModule(Set<Class<?>> classes) {
		this.classes = classes;
	}

	@Override
	public void configure() {
		this.classes.forEach(this::bind);
	}
}
