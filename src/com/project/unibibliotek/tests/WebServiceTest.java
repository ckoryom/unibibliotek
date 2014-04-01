package com.project.unibibliotek.tests;

import java.net.MalformedURLException;
import java.net.URL;

import com.project.unibibliotek.logic.WebService;

import junit.framework.TestCase;

public class WebServiceTest extends TestCase {
	
	private WebService webService;
	private String wsdlUrl;
	private URL url;
	
	public WebServiceTest() {
		webService = new WebService();
		wsdlUrl = "http://exlibris.com";
		try {
			url = new URL(wsdlUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testCheckWebServiceUrl() {
		assertTrue(webService.checkUrl(url));
	}
	
	public void testConnectToWebService() {
		assertTrue(webService.Connect());
	}
	
}
