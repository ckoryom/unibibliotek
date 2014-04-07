package com.project.unibibliotek.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.project.unibibliotek.model.ThreadStatus;

public class NetworkUtils {
	
	private ThreadStatus threadStatus;
	private HttpURLConnection conn;
	public InputStream downloadUrl(final String urlString) throws IOException {
		conn = null;
		threadStatus = ThreadStatus.NONE;
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        threadStatus = ThreadStatus.WORKING;
		        URL url = null;
				try {
					url = new URL(urlString);
					conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(10000 /* milliseconds */);
				    conn.setConnectTimeout(15000 /* milliseconds */);
				    conn.setRequestMethod("GET");
				    conn.setDoInput(true);
				    conn.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
		        threadStatus = ThreadStatus.FINISHED;
		        Thread.currentThread().interrupt();
		    }
		} );
		thread.start();
		while (threadStatus != ThreadStatus.FINISHED) {
			if (threadStatus == ThreadStatus.FINISHED)
				return conn.getInputStream();
		}
	    return conn.getInputStream();
	}
	
}
