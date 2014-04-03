package com.project.unibibliotek.logic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.utils.XmlUtils;

import android.util.Log;

import primo4j.PrimoX;
import primo4j.action.QueryResult;
import primo4j.action.QueryResultItem;
import primo4j.data.Query;
import primo4j.data.QueryCriteria;
import primo4j.util.PrimoException;

public class WebService {
	private String wsdl;
	
	private URL url;
	
	private static final String TAG = "WebService";
	
	private PrimoX primoService;
	
	public WebService(){
		wsdl = "bc-primo.hosted.exlibrisgroup.com";
		url = null;
		primoService = null;

	}
	
	public Boolean checkUrl() {
		
		HttpURLConnection huc;
		int responseCode;
		try {
			huc = (HttpURLConnection) url.openConnection();
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
		return url.getProtocol() + "://" + url.getHost();
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
	
	public List<Book> search(String scope, String title) {
		if(primoService != null) {
			Query query = new Query(scope, title);

			try {
				Log.d(TAG,"XSERVICE:" + query.getXservicePath());
				QueryResult queryResult = primoService.search(query);
				XmlUtils utils = new XmlUtils();
				String strXml = queryResult.getResponse().asString();
				Document xmlResult = utils.convertStringToDocument(strXml);
				Element rootElement = xmlResult.getDocumentElement();
				List<Book> books = new ArrayList<Book>();
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
	
	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}


	

}
