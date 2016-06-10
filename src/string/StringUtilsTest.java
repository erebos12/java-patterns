package string;

import org.junit.Test;

import test.TestHelper;

public class StringUtilsTest {

	private String CN = StringUtilsTest.class.getName();

	@Test
	public void testIsNull() {

		String MN = "testIsNull";
		String isNull = null;
		boolean result = StringUtils.isStringNullOrEmpty(isNull);
		if (result == false) {
			TestHelper.failTestWithErrorMsg(CN, MN, "result MUST be TRUE but it is: " + result);
		}
	}
	
	@Test
	public void testIsNotNullAndNotEmpty() {

		String MN = "testIsNotNullAndNotEmpty";
		String s = "NotNullAndNotEmpty";
		boolean result = StringUtils.isStringNullOrEmpty(s);
		if (result == true) {
			TestHelper.failTestWithErrorMsg(CN, MN, "result MUST be FALSE but it is: " + result);
		}
	}
	
	
	@Test
	public void testIsEmpty() {

		String MN = "testIsEmpty";
		String isEmpty = "";
		boolean result = StringUtils.isStringNullOrEmpty(isEmpty);
		if (result == false) {
			TestHelper.failTestWithErrorMsg(CN, MN, "result MUST be TRUE but it is: " + result);
		}
	}

	@Test
	public void testTrimIsEmpty() {

		String MN = "testTrimIsEmpty";
		String isEmpty = "     ";
		boolean result = StringUtils.isStringNullOrEmpty(isEmpty);
		if (result == false) {
			TestHelper.failTestWithErrorMsg(CN, MN, "result MUST be TRUE but it is: " + result);
		}
	}
}
