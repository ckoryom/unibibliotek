package com.project.unibibliotek.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.project.unibibliotek.model.Author;
import com.project.unibibliotek.model.Availability;
import com.project.unibibliotek.model.Book;
import com.project.unibibliotek.model.Location;
import com.project.unibibliotek.model.ResourceType;
import com.project.unibibliotek.model.WebServiceParameters;

import android.util.Xml;

public class XmlUtils {
	
	public Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try 
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }
	
	public List<Book> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            return parseXML(parser);
        } finally {
            in.close();
        }
    }
	
	public WebServiceParameters getWebServiceParameters() throws XmlPullParserException, IOException {
		InputStream in = null;
		WebServiceParameters parameters = null;
		try {
			
			in = getClass().getClassLoader().getResourceAsStream("webservice.xml");
			if (in != null) {
				XmlPullParser parser = Xml.newPullParser();
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in, null);
	            
	            int eventType = parser.getEventType();
	            while (eventType != XmlPullParser.END_DOCUMENT) {
	            	String name = null;
	            	switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if (name.equals("webservice")) {
								parameters = new WebServiceParameters();
							}
							else if (name.equals("wsdl")) {
								URL url = new URL(parser.nextText());
								parameters.setUrl(url);
							}
							else if (name.equals("scope")) {
								parameters.setScope(parser.nextText());
							}
							break;
						case XmlPullParser.END_TAG:
							name = parser.getName();
							if (name.equalsIgnoreCase("webservice")) {
								return parameters;
							}
						default:
							break;
					}
	            	eventType = parser.next();
	            }
	            return parameters;
			}
			return parameters;
		} finally {
            in.close();
        }
	}
	
	private List<Book> parseXML (XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Book> books = null;
		int eventType = parser.getEventType();
		Book currentBook = null;
		Author author = null;
		List<String> isbn = null;
		List<Location> locations = null;
		Location currentLocation = null;
		
		Boolean facets = false;
		Boolean addata = false;
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name = null;
			switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					books = new ArrayList<Book>();
					break;
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equals("record")) {
						currentBook = new Book();
						author = new Author();
						isbn = new ArrayList<String>();
						locations = null;
					}
					else if (name.equals("sear:LIBRARIES")) {
						locations = new ArrayList<Location>();
					}
					else if (locations != null) {
						if (name.equals("sear:collection")) {
							currentLocation = new Location();
							currentLocation.setSite(parser.nextText());
						}
						else if (name.equals("sear:callNumber")) {
							currentLocation.setSection(parser.nextText());
							locations.add(currentLocation);
							currentLocation = null;
						}
							
					}
					else if (currentBook != null) {
						if (name.equals("facets")) {
							addata = false;
							facets = true;
						}
						else if (name.equals("addata")) {
							facets = false;
							addata = true;
						}
						if (facets) {
							if (name.equals("topic")) {
								currentBook.setTopic(parser.nextText());
							}
							else if (name.equals("toplevel")) {
								String value = parser.nextText();
								if (value.equals("available"))
									currentBook.setAvailability(Availability.available);
								else if (value.equals("online_resources"))
									currentBook.setAvailability(Availability.online_resources);
							}
							else if (name.equals("rsrctype")) {
								String value = parser.nextText();
								if (value.equals("book"))
									currentBook.setResourceType(ResourceType.book);
								else if (value.equals("ebook"))
									currentBook.setResourceType(ResourceType.ebook);
							}
							
						}
						else if (addata) {
							if (name.equals("aulast")) {
								author.setLastName(parser.nextText());
							}
							else if (name.equals("aufirst")) {
								author.setName(parser.nextText());
							}
							else if (name.equals("au")) {
								author.setAuthorName(parser.nextText());
							}
							else if (name.equals("btitle")) {
								currentBook.setTitle(parser.nextText());
							}						
							else if (name.equals("date")) {
								currentBook.setYear(parser.nextText());
							}
							else if (name.equals("isbn")) {
								isbn.add(parser.nextText());
							}
							else if (name.equals("language")) {
								isbn.add(parser.nextText());
							}
							else if (name.equals("abstract")) {
								currentBook.setDescription(parser.nextText());
							}
							else if (name.equals("pub")) {
								currentBook.setPublisher(parser.nextText());
							}
						}
					}
					break;
				case XmlPullParser.END_TAG:
					name = parser.getName();
					if (name.equalsIgnoreCase("record") && currentBook != null) {
						currentBook.setAuthor(author);
						currentBook.setIsbn(isbn);
						books.add(currentBook);
					}
					else if (name.equalsIgnoreCase("sear:LIBRARIES") && locations != null) {
						if (books != null && books.size()>0) {
							int index = books.size()-1;
							Book previousBook = books.get(index);
							previousBook.setLocations(locations);
							books.set(index, previousBook);
							locations = null;
						}
					}
				default:
					break;
				}
			eventType = parser.next();
		}
		return books;
		
	}
	
}
