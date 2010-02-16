package com.github.cloudwrangler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;
import com.google.inject.Inject;

public class SecretKeyCache implements KeyResolver,KeyStash{

	@Inject
	Logger log;

	Cache cache;

	public SecretKeyCache() {

		Map props = new HashMap();
		props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
		try {
			CacheFactory cacheFactory = CacheManager.getInstance()
					.getCacheFactory();
			cache = cacheFactory.createCache(props);
		} catch (CacheException e) {
			log.log(Level.SEVERE, "Failed to init cache", e);
		}
	}

	public String putSecret(String secret) {
		String result = UUID.randomUUID().toString();
		cache.put(result, secret);
		return result;
	}

	public String getSecret(String key) {
		return (String) this.cache.get(key);
	}
}
