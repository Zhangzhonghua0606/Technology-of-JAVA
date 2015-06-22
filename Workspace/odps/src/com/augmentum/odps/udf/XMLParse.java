package com.augmentum.odps.udf;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.aliyun.odps.udf.UDF;
import com.qq.weixin.mp.aes.AesException;

public class XMLParse extends UDF {

	public String evaluate(String xmltext, String eleName) throws AesException {
		String value = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmltext);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist = root.getElementsByTagName(eleName);
			value = nodelist.item(0).getTextContent();

		} catch (Exception ex) {
			return value;
		}
		return value;
	}
}
