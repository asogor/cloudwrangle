import java.io.IOException;
import java.util.Properties;

import com.github.cloudwrangler.KeyResolver;
import com.github.cloudwrangler.SpreadSheetServiceFactory;
import com.github.cloudwrangler.cloudtree.ReportFormater;
import com.github.cloudwrangler.cloudtree.TextReportFormatter;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;


public class ReportTestModule extends AbstractModule{

	@Override
	protected void configure() {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getResourceAsStream("test.props"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bind(SpreadSheetServiceFactory.class).to(TestSpreadsheetFactoryImpl.class);		
		bind(ReportFormater.class).to(TextReportFormatter.class);
		bind(KeyResolver.class).to(FakeKeyResolver.class);
		Names.bindProperties(this.binder(),props);
	}

}
