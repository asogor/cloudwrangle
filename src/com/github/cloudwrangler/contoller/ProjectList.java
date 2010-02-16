package com.github.cloudwrangler.contoller;

import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.cloudwrangler.KeyResolver;
import com.github.cloudwrangler.SecretKeyCache;
import com.github.cloudwrangler.SpreadSheetServiceFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;

@At("/projects/:authToken")
public class ProjectList {

	@Inject
	Logger log;
	@Inject
	SpreadSheetServiceFactory factory;
	@Inject
	KeyResolver keyCache;

	List<SpreadsheetEntry> spreadsheets;

	String token;

	public List<SpreadsheetEntry> getSpreadsheets() {
		return spreadsheets;
	}

	public String getToken() {
		return token;
	}

	@Get
	public void show(@Named("authToken") String token) {
		SpreadsheetService service = factory.create(keyCache.getSecret(token));
		this.token = token;
		try {
			URL metafeedUrl = new URL(
					"http://spreadsheets.google.com/feeds/spreadsheets/private/full");
			SpreadsheetFeed feed = service.getFeed(metafeedUrl,
					SpreadsheetFeed.class);
			this.spreadsheets = feed.getEntries();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed to exchange for session token", e);
		}
	}
}
