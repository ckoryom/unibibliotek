package com.project.unibibliotek.test;

import junit.framework.TestSuite;;

public class ProjectTestSuite extends TestSuite {
	
	public static TestSuite suite() {
		final TestSuite t = new TestSuite();
		t.addTest(TestSuite.createTest(NetworkUtilsTest.class, "testDownloadUrl"));
		t.addTest(TestSuite.createTest(NetworkUtilsTest.class, "testLoadJson"));
		t.addTest(TestSuite.createTest(XmlUtilsTest.class, "testParse"));
		t.addTest(TestSuite.createTest(WebServiceTest.class, "testCheckWebServiceUrl"));
		t.addTest(TestSuite.createTest(WebServiceTest.class, "testConnectToWebService"));
		t.addTest(TestSuite.createTest(WebServiceTest.class, "testSearch"));
		t.addTest(TestSuite.createTest(WebServiceTest.class, "testSearchBySubject"));
		t.addTest(TestSuite.createTest(WebServiceTest.class, "testSearchByAuthor"));
		t.addTest(TestSuite.createTest(WebServiceTest.class, "testSearchByTitle"));
		t.addTest(TestSuite.createTest(WebServiceTest.class, "testSearchByISBN"));
		t.addTest(TestSuite.createTest(GoogleRestfulTest.class, "testBuildRestfulUrl"));
		t.addTest(TestSuite.createTest(GoogleRestfulTest.class, "testExtractInfo"));
		return t;
	}
	
}
