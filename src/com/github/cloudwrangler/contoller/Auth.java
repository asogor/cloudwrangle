package com.github.cloudwrangler.contoller;

import java.util.logging.Logger;

import com.google.gdata.client.http.AuthSubUtil;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;
import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;

@RequestScoped
@At("/auth")
public class Auth {

	@Inject
	Logger log;
	
	@Named("hostname")
	@Inject
	String hostName;
	
	String requestUrl;
	
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Get
	public void create()
	{
		log.info("Creating auth");
		this.requestUrl = AuthSubUtil.getRequestUrl(hostName,
                "http://spreadsheets.google.com/feeds",
                false,
                true);
	}

	public String getHostName() {
		return hostName;
	}
}
