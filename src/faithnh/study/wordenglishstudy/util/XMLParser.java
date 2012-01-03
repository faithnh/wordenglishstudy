package faithnh.study.wordenglishstudy.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

public class XMLParser {
	public static ArrayList<String> getTagNameInfo(String tagName, String html)
		throws Exception{
		ArrayList<String> result;
		int eventType;
		final XmlPullParser xmlPullParser;
		
		result= new ArrayList<String>();
		xmlPullParser = Xml.newPullParser();
		xmlPullParser.setInput(new StringReader(html));
		
		for(eventType = xmlPullParser.getEventType();
				eventType != XmlPullParser.END_DOCUMENT;
				eventType = xmlPullParser.next()){
			if(eventType == XmlPullParser.START_TAG &&
					xmlPullParser.getName().equals(tagName)){
				result.add(xmlPullParser.nextText());
			}
		}
		return result;
	}
	
	public static ArrayList<String> getTagNameInfo(String tagName,
			String className, String html)
			throws Exception{
			ArrayList<String> result;
			int eventType;
			final XmlPullParser xmlPullParser;
			
			result= new ArrayList<String>();
			xmlPullParser = Xml.newPullParser();
			xmlPullParser.setInput(new StringReader(html));
			
			for(eventType = xmlPullParser.getEventType();
					eventType != XmlPullParser.END_DOCUMENT;
					eventType = xmlPullParser.next()){
				if(eventType == XmlPullParser.START_TAG &&
						xmlPullParser.getName().equals(tagName) &&
						xmlPullParser.getClass().equals(className)){
					result.add(xmlPullParser.nextText());
				}
			}
			return result;
	}
	public static String getXPath(String xpathString, byte[] xml) throws Exception{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(xml));
        
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        String result = xpath.evaluate(xpathString, document);
        return result;
	}
}

