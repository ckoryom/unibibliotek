package com.project.unibibliotek.logic;

import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.BookImage;
import com.project.unibibliotek.utils.NetworkUtils;

public class GoogleBooksRestful {
	
	private NetworkUtils networkUtils;
	
	public GoogleBooksRestful () {
		networkUtils = new NetworkUtils();
	}
	
	public String buildRestfulUrl (String isbn) {
		isbn = isbn.replace("-", "");
		return "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
	}
	
	public Book extractInfo (Book book) {
		try {
			if (book.getIsbn() != null && book.getIsbn().size() > 0) {
				Boolean flag = false;
				int position = 0;
				while (!flag && position < book.getIsbn().size()) {
					String isbn = book.getIsbn().get(position);
					String url = buildRestfulUrl(isbn);
					JSONObject json = networkUtils.loadJsonFromUrl(url);
					if (json != null) {
						int size = (Integer)json.get("totalItems");
						if (size > 0) {
							JSONArray array = (JSONArray) json.get("items");
							JSONObject item = (JSONObject) array.get(0);
							JSONObject volume = (JSONObject) item.get("volumeInfo");
							JSONObject images = (JSONObject) volume.get("imageLinks");
							String smallImage = images.getString("smallThumbnail");
							String largeImage = images.getString("thumbnail");
							BookImage bookImage = new BookImage();
							bookImage.setSmallImage(smallImage);
							bookImage.setLargeImage(largeImage);
							flag = true;
							break;
							
						}
						else
							position++;
					}
				}
				
			}
			return book;
		}
		catch (Exception e) {
			Log.e("GoogleBooksRestful", "Error getting data from RESTFUL GOOGLE");
			return book;
		}
	}
	
}
