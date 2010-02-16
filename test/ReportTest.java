import org.junit.Test;

import com.github.cloudwrangler.cloudtree.NodeParseError;
import com.github.cloudwrangler.contoller.Report;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class ReportTest {

	@Test
	public void testReport() throws Exception
	{
		Injector injector = Guice.createInjector(new ReportTestModule());
		Report report = injector.getInstance(Report.class);
		report.get("PJC Feature Tree",null);
		System.out.println(report.getFormater().getText());
		for (NodeParseError error : report.getErrorLog()) {
			System.out.println("Error: " + error);
		}
	}
}
