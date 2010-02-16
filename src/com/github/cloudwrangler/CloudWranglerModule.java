package com.github.cloudwrangler;

import com.github.cloudwrangler.cloudtree.ReportFormater;
import com.github.cloudwrangler.cloudtree.TextReportFormatter;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class CloudWranglerModule extends AbstractModule{

	@Override
	protected void configure() {
		SecretKeyCache keyCache = new SecretKeyCache();
		bind(SpreadSheetServiceFactory.class).to(SpreadSheetServiceFactoryImpl.class);
		bind(ReportFormater.class).to(TextReportFormatter.class);
		bind(String.class).annotatedWith(Names.named("hostname")).toProvider(HostNameProvider.class);
		bind(KeyResolver.class).toInstance(keyCache);
		bind(KeyStash.class).toInstance(keyCache);
	}

}
