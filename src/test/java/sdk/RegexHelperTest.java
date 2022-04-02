package sdk;

import com.google.common.base.Strings;
import com.sdk.RegexHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class RegexHelperTest {

	private Pattern pattern;

	@BeforeEach
	public void setUp() {
		this.pattern = Pattern.compile("rest\\.client\\.([-_\\w]+)\\..*");
	}

	@Test
	public void match_ok() {
		String actual = RegexHelper.getString(pattern.matcher("rest.client.currency.pool.name"), 1);
		assertTrue(!Strings.isNullOrEmpty(actual));
	}

	@Test
	public void match_error() {
		String actual = RegexHelper.getString(pattern.matcher("another_key"), 1);
		assertTrue(Strings.isNullOrEmpty(actual));
	}
}
