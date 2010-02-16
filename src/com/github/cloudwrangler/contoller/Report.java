package com.github.cloudwrangler.contoller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.github.cloudwrangler.KeyResolver;
import com.github.cloudwrangler.SecretKeyCache;
import com.github.cloudwrangler.SpreadSheetServiceFactory;
import com.github.cloudwrangler.cloudtree.CloudTree;
import com.github.cloudwrangler.cloudtree.NodeParseError;
import com.github.cloudwrangler.cloudtree.ReportFormater;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;
import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;

@At("/report/:name/:token")
public class Report {

	@Inject
	Logger log;

	@Inject
	SpreadSheetServiceFactory factory;
	
	@Inject
	ReportFormater formater;
	
	@Inject
	KeyResolver keyCache;
	
	List<NodeParseError> errorLog = new ArrayList<NodeParseError>();

	CloudTree tree = new CloudTree();
	
	@Get
	public void get(@Named("name") String report,@Named("token") String token) throws Exception {
		SpreadsheetService service = factory.create(keyCache.getSecret(token));
		URL metafeedUrl = new URL(
				"http://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetQuery query = new SpreadsheetQuery(metafeedUrl);
		SpreadsheetFeed feed = service.getFeed(query, SpreadsheetFeed.class);
		for (WorksheetEntry workSheet : feed.getEntries().get(0)
				.getWorksheets()) {
			if (workSheet.getTitle().getPlainText().equals("FeatureTree")) {
				parseFeatureTree(workSheet.getListFeedUrl(), service);
			}
		}
		tree.report(formater);
	}

	public List<NodeParseError> getErrorLog() {
		return errorLog;
	}

	public ReportFormater getFormater() {
		return formater;
	}

	protected void parseFeatureTree(URL feedUrl, SpreadsheetService service)
			throws Exception {
		ListFeed feed = service.getFeed(feedUrl, ListFeed.class);
		for (ListEntry entry : feed.getEntries()) {
			tree.addNode(entry.getCustomElements().getValue("tree"), entry
					.getCustomElements().getValue("usecase"), entry
					.getCustomElements().getValue("target"), entry
					.getCustomElements().getValue("confidence"), entry
					.getCustomElements().getValue("estimate"), errorLog);
		}
	}
}
