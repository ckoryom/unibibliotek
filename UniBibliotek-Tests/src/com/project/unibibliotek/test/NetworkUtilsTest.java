package com.project.unibibliotek.test;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.util.Log;

import com.project.unibibliotek.utils.NetworkUtils;


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
	
	public void testLoadJson() {
		String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:382732274X";
		JSONObject json = networkUtils.loadJsonFromUrl(url);
		assertNotNull(json);
		try {
			assertTrue(json.getString("kind").equals("books#volumes"));
		} catch (JSONException e) {
			Log.e("JsonTest", "Failed to load JSON");
		}
	}
	
	public void testConvertBitmap() {
		String url = "http://bks1.books.google.ie/books?id=4vRE_Q41ZHEC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api";
		Bitmap bitmap = networkUtils.getBitmapFromUrl(url);
		assertNotNull(bitmap);
	}
	
}
