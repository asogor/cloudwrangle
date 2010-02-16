import com.github.cloudwrangler.KeyResolver;


public class FakeKeyResolver implements KeyResolver {

	@Override
	public String getSecret(String key) {
		return null;
	}

}
