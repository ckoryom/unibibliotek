package com.project.unibibliotek.logic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;



import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.SearchFilter;
import com.project.unibibliotek.model.WebServiceParameters;
import com.project.unibibliotek.utils.NetworkUtils;
import com.project.unibibliotek.utils.XmlUtils;


import android.util.Log;

import primo4j.PrimoX;

import primo4j.data.Query;
import primo4j.data.QueryCriteria;


public class WebService {

	private static final String TAG = "WebService";
	
	private PrimoX primoService;
	
	private WebServiceParameters parameters;
	
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
		try {
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
	
	public List<Book> search(List<SearchFilter> searchFilters) {
		if(primoService != null) {
			books = null;
			Query query = null;
			int filterCount = 0;
			for (SearchFilter filter: searchFilters) {
				if (filterCount == 0) {
					query = new Query(parameters.getScope(), filter.getFilterValue(), "contains", filter.getQuery());
				}
				else {
					query.addCriteria(new QueryCriteria(filter.getFilterValue(), "contains", filter.getQuery()));
				}
				filterCount++;
			}
			try {
				XmlUtils xmlUtils = new XmlUtils();
				NetworkUtils networkUtils = new NetworkUtils();
				String xServicePath = query.getXservicePath();
				InputStream stream = networkUtils.downloadUrl(getFullUrl() + xServicePath);
				
				books = new ArrayList<Book>();
				books = xmlUtils.parse(stream);
				
			}
			catch (Exception e){
				Log.e(TAG,"Problem while searching primo: " + e.getMessage());
			}
			return books;
			
		}
		else{
			Log.e(TAG,"PrimoService is null");
			if (books != null && books.size() > 0)
				return books;
			else
				return null;
		}
	}

}
