package com.project.unibibliotek.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.project.unibibliotek.logic.WebService;
import com.project.unibibliotek.model.Book;

public class WebServiceTest extends AndroidTestCase {
	private WebService webService;
	
	protected void setUp(){
		webService = new WebService();
	}
	
	public void testCheckWebServiceUrl() {
		assertTrue(webService.checkUrl());
	}
	
	public void testConnectToWebService() {
		assertTrue(webService.connect());
	}
	
	public void testSearch() {
		String title = "c++";
		webService.connect();
		List<Book> books = webService.search(title);
		assertTrue(books.size()>0);
	}
}
