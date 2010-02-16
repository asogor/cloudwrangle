package com.github.cloudwrangler;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class HostNameProvider implements Provider<String> {

	@Inject
	HttpServletRequest request;
	
	@Override
	public String get() {
		return request.getRequestURL().toString().replace("auth", "authcomplete");
	}
}
