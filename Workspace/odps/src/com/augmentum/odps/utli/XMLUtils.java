package com.augmentum.odps.utli;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtils {

    public static Map<String, Object> parseXMLByDom4j(String text) throws DocumentException {
    	Document documnet = DocumentHelper.parseText(text);
    	Element root = documnet.getRootElement();
    	Iterator iterator = root.elementIterator();
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	while (iterator.hasNext()) {
    		Element element = (Element) iterator.next();
    		result.put(element.getName(), element.getText());
    	}
    	return result;
    }
    
    public static void parseXML(String text) throws Exception, IOException {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(text);
		InputSource is = new InputSource(sr);
		org.w3c.dom.Document document = db.parse(is);
		
		org.w3c.dom.Node node = document.getElementsByTagName("xml").item(0);
        NodeList list = node.getChildNodes();
        
        for(int i = 0; i < list.getLength(); i++)  
        {  
            System.out.println(list.item(i).getNodeName() + ":" + list.item(i).getTextContent());
        }  
    }  
}

