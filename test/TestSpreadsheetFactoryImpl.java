import com.github.cloudwrangler.SpreadSheetServiceFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.AuthenticationException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class TestSpreadsheetFactoryImpl implements
		SpreadSheetServiceFactory {

	@Named("testuser")
	@Inject
	String username;
	@Named("testpassword")
	@Inject
	String password;

	@Override
	public SpreadsheetService create(String token) {
		SpreadsheetService myService = new SpreadsheetService(
				"cloudwranglertest");
		try {
			myService.setUserCredentials(username, password);
			return myService;
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
