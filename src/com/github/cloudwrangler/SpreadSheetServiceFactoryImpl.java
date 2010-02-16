package com.github.cloudwrangler;

import com.google.gdata.client.spreadsheet.SpreadsheetService;

public class SpreadSheetServiceFactoryImpl implements SpreadSheetServiceFactory {

	@Override
	public SpreadsheetService create(String authToken) {
		SpreadsheetService service = new SpreadsheetService("cloudwrangler");
		if(authToken != null)
		{
			service.setAuthSubToken(authToken, null);
		}
		return service;
	}

}
