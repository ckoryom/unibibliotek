package com.project.unibibliotek.logic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;



import com.project.unibibliotek.model.Book;
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
		
		HttpURLConnection huc;
		int responseCode;
		try {
			huc = (HttpURLConnection) parameters.getUrl().openConnection();
			responseCode = huc.getResponseCode();
			if (responseCode != 404) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
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
	
	public List<Book> search(String title) {
		if(primoService != null) {
			Query query = new Query(parameters.getScope(), title);

			try {
				XmlUtils xmlUtils = new XmlUtils();
				NetworkUtils networkUtils = new NetworkUtils();

				InputStream stream = networkUtils.downloadUrl(getFullUrl() + query.getXservicePath());
				
				List<Book> books = new ArrayList<Book>();
				books = xmlUtils.parse(stream);
				if(books.isEmpty())
					return null;
				else
					return books;
			}
			catch (Exception e){
				Log.e(TAG,"Problem while searching primo: " + e.getMessage());
				return null;
			}
		}
		else{
			Log.e(TAG,"PrimoService is null");
			return null;
		}
	}

}
