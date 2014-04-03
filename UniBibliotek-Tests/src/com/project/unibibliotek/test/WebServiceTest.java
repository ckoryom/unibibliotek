package com.project.unibibliotek.test;

import java.net.MalformedURLException;
import java.net.URL;

import android.test.AndroidTestCase;

import com.project.unibibliotek.logic.WebService;

public class WebServiceTest extends AndroidTestCase {
	private WebService webService;
	private String wsdlUrl;
	private URL url;
	
	protected void setUp(){
		webService = new WebService();
		//wsdlUrl = "http://primo.getty.edu";
		//wsdlUrl = "http://ubz-primo-test.hosted.exlibrisgroup.com/PrimoWebServices/services/tags";
		wsdlUrl = "http://bc-primo.hosted.exlibrisgroup.com/PrimoWebServices/services/tags";
		try {
			url = new URL(wsdlUrl);
			webService.setUrl(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testCheckWebServiceUrl() {
		assertTrue(webService.checkUrl());
	}
	
	public void testConnectToWebService() {
		assertTrue(webService.connect());
	}
	
	public void testSearch() {
		//String scope = "GETTY_ALMA";
		//String scope = "39UBZ";
		String scope = "BCL";
		String title = "c++";
		webService.connect();
		assertTrue(webService.search(scope, title).size()>0);
	}
}
