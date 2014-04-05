package com.project.unibibliotek.test;

import java.io.IOException;

import android.test.AndroidTestCase;

import com.project.unibibliotek.utils.NetworkUtils;

import junit.framework.TestCase;

public class NetworkUtilsTest extends AndroidTestCase {

	private String urlString;
	
	private NetworkUtils networkUtils;
	
	public void setUp() {
		urlString = "http://www.w3schools.com/xml/cd_catalog.xml";
		networkUtils = new NetworkUtils();
	}
	
	public void testDownloadUrl() {
		try {
			assertNotNull(networkUtils.downloadUrl(urlString));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
