package com.cp.utils.xml;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * SQL池
 * 
 * @author zengxm 2015年2月7日
 * 
 */
public class SQLPool {

	public static String getSQL(String xmlpath, String id) {
		String sql = "";
		try {
			Document doc = XMLTools.parseDocument(xmlpath);
			Element node = doc.getElementById(id);
			if (node != null) {
				sql = node.getFirstChild().getNodeValue();
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return sql;
	}

	public static String getSQL(URL url, String id) {
		String sql = "";
		try {
			Document doc = XMLTools.parseDocument(url);
			Element node = doc.getElementById(id);
			if (node != null) {
				sql = node.getLastChild().getNodeValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
	}
}
