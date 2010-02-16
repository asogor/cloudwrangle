package com.github.cloudwrangler;

import com.google.gdata.client.spreadsheet.SpreadsheetService;

public interface SpreadSheetServiceFactory {

	SpreadsheetService create(String authToken);
	
}
