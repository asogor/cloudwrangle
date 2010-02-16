package com.github.cloudwrangler.contoller;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.cloudwrangler.KeyStash;
import com.github.cloudwrangler.SecretKeyCache;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;

@RequestScoped
@At("/authcomplete")
public class AuthComplete {

	@Inject 
	Logger log;
	
	@Inject
	KeyStash keyCache;
	
	String sessionToken;
	
	private String token;

	public String getSessionToken() {
		return sessionToken;
	}

	public String getToken() {
		return token;
	}
	
	@Get
	public void login()
	{
		try {
			sessionToken = keyCache.putSecret(AuthSubUtil.exchangeForSessionToken(token, null));
			log.info("Got session token ready to rumble!");
		} catch (Exception e) {
			log.log(Level.SEVERE,"Faild to exchange for session token",e);
		}
	}

	public void setToken(String token) {
		this.token = token;
	}
}
