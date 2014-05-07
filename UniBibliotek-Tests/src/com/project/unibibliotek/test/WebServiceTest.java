package com.project.unibibliotek.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

import com.project.unibibliotek.logic.WebService;
import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.Filter;
import com.project.unibibliotek.model.SearchFilter;

public class WebServiceTest extends AndroidTestCase {
	private WebService webService;
	
	private List<SearchFilter> searchFilters;
	
	protected void setUp(){
		webService = new WebService();
		searchFilters = new ArrayList<SearchFilter>();
	}
	
	public void testCheckWebServiceUrl() {
		assertTrue(webService.checkUrl());
	}
	
	public void testConnectToWebService() {
		assertTrue(webService.connect());
	}
	
	public void testSearch() {
		String anything = "c++";
		webService.connect();
		SearchFilter anythingFilter = new SearchFilter(Filter.ANY, anything);
		searchFilters.add(anythingFilter);
		List<Book> books = webService.search(searchFilters);
		assertTrue(books.size()>0);
	}
	
	public void testSearchByTitle() {
		String title = "c++";
		webService.connect();
		SearchFilter titleFilter = new SearchFilter(Filter.ANY, title);
		searchFilters.add(titleFilter);
		List<Book> books = webService.search(searchFilters);
		assertTrue(books.size()>0);
	}
	
	public void testSearchByAuthor () {
		String author = "tolkien";
		webService.connect();
		SearchFilter authorFilter = new SearchFilter(Filter.AUTHOR, author);
		searchFilters.add(authorFilter);
		List<Book> books = webService.search(searchFilters);
		assertTrue(books.size()>0);
	}
	
	public void testSearchBySubject () {
		String subject = "programming";
		webService.connect();
		SearchFilter subjectFilter = new SearchFilter(Filter.SUBJECT, subject);
		searchFilters.add(subjectFilter);
		List<Book> books = webService.search(searchFilters);
		assertTrue(books.size()>0);
	}
	
	public void testSearchByISBN () {
		String isbn = "9780585135823";
		webService.connect();
		SearchFilter isbnFilter = new SearchFilter(Filter.ISBN, isbn);
		searchFilters.add(isbnFilter);
		List<Book> books = webService.search(searchFilters);
		assertTrue(books.size()>0);
	}
	
	public void testSearchByMultipleFilters () {
		String subject = "programming";
		String title = "java";
		webService.connect();
		SearchFilter filter = new SearchFilter(Filter.SUBJECT, subject);
		searchFilters.add(filter);
		filter = new SearchFilter(Filter.TITLE, title);
		searchFilters.add(filter);
		List<Book> books = webService.search(searchFilters);
		assertTrue(books.size()>0);
	}
}
