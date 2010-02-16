package com.github.cloudwrangler;

import com.github.cloudwrangler.contoller.Auth;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.sitebricks.SitebricksModule;

public class AppGuiceCreator extends GuiceServletContextListener {

	@Override
	public Injector getInjector() {
		return Guice.createInjector(new SitebricksModule() {
			@Override
			protected void configureSitebricks() {
				scan(Auth.class.getPackage());
			}
		},new CloudWranglerModule());
	}
}
