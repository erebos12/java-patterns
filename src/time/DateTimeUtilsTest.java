package time;

import org.junit.Test;

import string.StringUtils;
import test.TestHelper;
import util.logging.Logger;

public class DateTimeUtilsTest {

	private String CN = DateTimeUtilsTest.class.getName();
	
	@Test
	public void test() {
		String MN = "test()";
		String timestamp = DateTimeUtils.getCurrentDateTimeAsString();
		if (StringUtils.isStringNullOrEmpty(timestamp))
		{
			TestHelper.failTestWithErrorMsg(CN, MN, "timestamp MUST NOT be null or empty!");
		}
		Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "timestamp: " + timestamp);
	}

}
