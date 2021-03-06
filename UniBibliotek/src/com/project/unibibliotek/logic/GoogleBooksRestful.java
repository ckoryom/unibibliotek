package com.project.unibibliotek.logic;

import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.BookImage;
import com.project.unibibliotek.model.Rating;
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
							JSONObject images = null;
							String smallImage = "";
							String largeImage = "";
							if (volume.has("imageLinks")) {
								images = (JSONObject) volume.get("imageLinks");
								smallImage = images.getString("smallThumbnail");
								largeImage = images.getString("thumbnail");
							}
							if (volume.has("description")) {
								book.setDescription(volume.getString("description"));
							}
							BookImage bookImage = new BookImage();
							bookImage.setSmallImage(smallImage);
							bookImage.setLargeImage(largeImage);
							book.setImages(bookImage);
							book.setNumberPages(volume.getInt("pageCount"));
							Rating rating = null;
							if (volume.has("ratingsCount")) {
								rating = new Rating();
								rating.setTotalRatings(volume.getInt("ratingsCount"));
								rating.setAverageRating(volume.getInt("averageRating"));
							}
							book.setRating(rating);
							flag = true;
							break;
							
						}
						else
							position++;
					}
					else {
						return book;
					}
				}
				return book;
				
			}
			return book;
		}
		catch (Exception e) {
			Log.e("GoogleBooksRestful", "Error getting data from RESTFUL GOOGLE");
			return book;
		}
	}
	
}
