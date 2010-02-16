import java.util.regex.Matcher;

import org.junit.Test;

import com.github.cloudwrangler.cloudtree.CloudTree;



public class RegexpTest {

	@Test
	public void testparse()
	{
		String input = "{PJC}{UI}{Game Picker}{Display-C/ontrol}";
		Matcher matcher = CloudTree.treeValidator.matcher(input);
		System.out.println(matcher.groupCount());
		System.out.println(matcher.matches());
		System.out.println(input.substring(1, input.length()-1));
		System.out.println(input.substring(1, input.length()-1).split("\\}\\{").length);
	}
	
	@Test
	public void testestimate()
	{
		String input = "1000,1000";
		Matcher matcher = CloudTree.estimateValidator.matcher(input);
		System.out.println(matcher.groupCount());
		System.out.println(matcher.matches());
	}
}
