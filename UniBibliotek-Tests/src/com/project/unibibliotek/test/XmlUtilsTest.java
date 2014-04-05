package com.project.unibibliotek.test;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import android.test.AndroidTestCase;

import com.project.unibibliotek.utils.NetworkUtils;
import com.project.unibibliotek.utils.XmlUtils;

public class XmlUtilsTest extends AndroidTestCase {

	private String strXml;
	
	private XmlUtils xmlUtils;
	
	
	public void setUp() {
		strXml = "http://ubz-primo-test.hosted.exlibrisgroup.com/PrimoWebServices/xservice/search/brief?institution=39UBZ&onCampus=false&query=any,exact,pigs&indx=1&bulkSize=2&dym=true&highlight=true&lang=eng";
		xmlUtils = new XmlUtils();
	}
	
	public void testParse() {
		NetworkUtils networkUtils = new NetworkUtils();
		try {
			InputStream stream = networkUtils.downloadUrl(strXml);
			assertNotNull(xmlUtils.parse(stream));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
	}
	
}
