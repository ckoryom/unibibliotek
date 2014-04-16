package com.project.unibibliotek.test;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;

import com.project.unibibliotek.logic.GoogleBooksRestful;
import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.utils.NetworkUtils;


public class GoogleRestfulTest extends AndroidTestCase {

	private String isbn;
		
	private GoogleBooksRestful restful;
	
	private Book book;
	
	public void setUp() {
		
		restful = new GoogleBooksRestful();
		
		isbn = "3-827-3227-4X";
		
		book = new Book();
		
	}
	
	public void testBuildRestfulUrl () {
		String url = restful.buildRestfulUrl(isbn);
		assertTrue(url.equals("https://www.googleapis.com/books/v1/volumes?q=isbn:382732274X"));
	}
	
	public void testExtractInfo () {
		List<String> isbns = new ArrayList<String>();
		isbns.add(isbn);
		book.setIsbn(isbns);
		book = restful.extractInfo(book);
		assertNotNull(book);
		NetworkUtils networkUtils = new NetworkUtils();
		Bitmap testSmallBitmap = networkUtils.getBitmapFromUrl("http://bks7.books.google.ie/books?id=n8JyngEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api");
		Bitmap testLargeBitmap = networkUtils.getBitmapFromUrl("http://bks7.books.google.ie/books?id=n8JyngEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
		Bitmap smallBitmap = networkUtils.getBitmapFromUrl(book.getImages().getSmallImage());
		Bitmap largeBitmap = networkUtils.getBitmapFromUrl(book.getImages().getLargeImage());
		assertTrue(testSmallBitmap.getHeight() == smallBitmap.getHeight());
		assertTrue(testLargeBitmap.getHeight() == largeBitmap.getHeight());
		assertNotNull(book.getRating().getAverageRating());
		assertNotNull(book.getRating().getTotalRatings());
	}
	
}
