package com.project.unibibliotek.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONException;
import org.json.JSONObject;

import com.project.unibibliotek.model.ThreadStatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class NetworkUtils {
	
	private HttpURLConnection conn;
	
	private Bitmap bitmap;
	
	private ThreadStatus threadStatus;
	
	public InputStream downloadUrl(final String urlString) throws IOException {
		conn = null;
		URL url = null;
		try {
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
		    conn.setConnectTimeout(15000 /* milliseconds */);
		    conn.setRequestMethod("GET");
		    conn.setDoInput(true);
		    conn.connect();
		    return conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String jsonStringBuilder (Reader reader) throws IOException {
	    try {
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	public JSONObject loadJsonFromUrl (String url) {
		try {
			InputStream stream = downloadUrl(url);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
			String jsonString = jsonStringBuilder(buffer);
			JSONObject json = new JSONObject(jsonString);
			return json;
		}
		catch (JSONException je) {
			Log.e("JSON Error in NetworkUtils", je.getMessage());
			return null;
		}
		catch (Exception e) {
			Log.e("JSON Error in NetworkUtils", e.getMessage());
			return null;
		}
		
	}
	
	public Bitmap getBitmapFromUrl (final String stringUrl) {
		bitmap = null;
		Thread thread = new Thread (new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(stringUrl);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream stream = conn.getInputStream();
					bitmap = BitmapFactory.decodeStream(stream);
				}
				catch (IOException e) {
					e.printStackTrace();
					Log.e("BitmapConversion", "Error converting bitmap from url.");
					bitmap = null;
				}
				threadStatus = ThreadStatus.FINISHED;
		        Thread.currentThread().interrupt();

			}
		});
		threadStatus = ThreadStatus.WORKING;
		thread.start();
		while (threadStatus != ThreadStatus.FINISHED) {
			if (threadStatus == ThreadStatus.FINISHED)
				return bitmap;
		}
		return bitmap;


	}
}
