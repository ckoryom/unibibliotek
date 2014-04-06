package com.project.unibibliotek.logic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;



import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.ThreadStatus;
import com.project.unibibliotek.model.WebServiceParameters;
import com.project.unibibliotek.utils.NetworkUtils;
import com.project.unibibliotek.utils.XmlUtils;


import android.util.Log;

import primo4j.PrimoX;

import primo4j.data.Query;


public class WebService {

	private static final String TAG = "WebService";
	
	private PrimoX primoService;
	
	private WebServiceParameters parameters;
	
	private ThreadStatus threadStatus;
	
	private List<Book> books;
	
	public WebService(){
		parameters = null;
		primoService = null;
		XmlUtils xmlUtils = new XmlUtils();
		try {
			parameters = xmlUtils.getWebServiceParameters();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean checkUrl() {
		final List<Boolean> results = new ArrayList<Boolean>();
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		        	threadStatus = ThreadStatus.WORKING;
		        	HttpURLConnection huc;
		    		int responseCode;
		    		try {
		    			huc = (HttpURLConnection) parameters.getUrl().openConnection();
		    			responseCode = huc.getResponseCode();
		    			if (responseCode != 404) {
		    				results.add(true);
		    			} else {
		    				results.add(false);
		    			}
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    			results.add(false);
		    		}
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        threadStatus = ThreadStatus.FINISHED;
		        Thread.currentThread().interrupt();
		    }
		} );
		threadStatus = ThreadStatus.NONE;
		thread.start(); 
		while (threadStatus != ThreadStatus.FINISHED) {
			if (results.size()>0)
				return results.get(0);
		}
		return results.get(0);
	}
	
	private String getFullUrl(){
		return parameters.getUrl().getProtocol() + "://" + parameters.getUrl().getHost();
	}
	
	public Boolean connect() {
		try{
			if (checkUrl()) {
				primoService = new PrimoX(getFullUrl());
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e){
			Log.e(TAG,e.getMessage());
			return false;
		}
	}
	
	public List<Book> search(final String title) {
		if(primoService != null) {
			threadStatus = ThreadStatus.NONE;
			books = null;
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					threadStatus = ThreadStatus.WORKING;
					Query query = new Query(parameters.getScope(), title);

					try {
						XmlUtils xmlUtils = new XmlUtils();
						NetworkUtils networkUtils = new NetworkUtils();

						InputStream stream = networkUtils.downloadUrl(getFullUrl() + query.getXservicePath());
						
						books = new ArrayList<Book>();
						books = xmlUtils.parse(stream);
						
					}
					catch (Exception e){
						Log.e(TAG,"Problem while searching primo: " + e.getMessage());
					}
					threadStatus = ThreadStatus.FINISHED;
					Thread.currentThread().interrupt();
					
					
					
				}
			});
			thread.start();
			while (threadStatus != ThreadStatus.FINISHED) {
				if (threadStatus == ThreadStatus.FINISHED)
					return books;
			}
			return books;
			
		}
		else{
			Log.e(TAG,"PrimoService is null");
			return null;
		}
	}

}
