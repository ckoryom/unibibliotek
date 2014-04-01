package com.project.unibibliotek.logic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {
	private String wsdl;
	
	private URL url;
	
	public WebService(){
		wsdl = "";
		url = null;

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

	public Boolean Connect() {
		// TODO Auto-generated method stub
		return false;
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
