package com.project.unibibliotek.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.project.unibibliotek.model.Book;

import android.util.Log;
import android.util.Xml;

public class XmlUtils {
	
	private static final String ns = null;	
	
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
            parser.setInput(in, "utf-8");
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
	
//	private List<Book> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
//	    List<Book> entries = new ArrayList<Book>();
//
//	    parser.require(XmlPullParser.START_TAG, ns, "sear:SEGMENTS");
//	    while (parser.next() != XmlPullParser.END_TAG || parser.isEmptyElementTag()) {
//	        if (parser.isEmptyElementTag()) {
//	        	parser.next();
//	        }
//	    	if (parser.getEventType() != XmlPullParser.START_TAG) {
//	            continue;
//	        }
//	        String name = parser.getName();
//	        // Starts by looking for the entry tag
//	        if (name.equals("record")) {
//	            entries.add(readBook(parser));
//	        } else {
//	            parser = skip(parser);
//	            name = parser.getName();
//	            Log.d("a","ERROR:"+name);
//	        }
//	    }  
//	    return entries;
//	}
	
	private List<Book> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
	    List<Book> entries = new ArrayList<Book>();
	    while (parser.next() != XmlPullParser.END_DOCUMENT) {

	        String name = parser.getName();
	        // Starts by looking for the entry tag
	        if (name != null) {
		        if (name.equals("display")) {
		            entries.add(readBook(parser));
		        }
	        }
	    }  
	    return entries;
	}
	
	
	private Book readBook(XmlPullParser parser) throws XmlPullParserException, IOException {
	    parser.require(XmlPullParser.START_TAG, ns, "display");
	    Book book = new Book();
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        if (name.equals("title")) {
	            book.setTitle(readTitle(parser));
	        } else {
	            parser = skip(parser);
	        }
	    }
	    return book;
	}
	
	private XmlPullParser skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	    return parser;
	 }
	
	private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "title");
	    String title = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "title");
	    return title;
	}
	
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
	
}
