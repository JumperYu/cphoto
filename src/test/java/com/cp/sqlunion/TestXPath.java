package com.cp.sqlunion;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestXPath {

	private static String xml_uri = "com/cp/subject/service/subject.xml";//

	public static void main(String[] args) {
		for (int i = 1; i <= 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					read();
				}
			}).start();
		}
	}

	public static void read() {
		for (int i = 1; i <= 5; i++) {
			try {
				long begin = System.currentTimeMillis();
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = dbf.newDocumentBuilder();
				InputStream in = TestXPath.class.getClassLoader()
						.getResourceAsStream(xml_uri);
				Document doc = builder.parse(in);
				XPathFactory factory = XPathFactory.newInstance();
				XPath xpath = factory.newXPath();
				// 选取所有class元素的name属性
				// XPath语法介绍： http://w3school.com.cn/xpath/
				XPathExpression expr = xpath
						.compile("//sql[@id='find_subjects']");
				NodeList nodes = (NodeList) expr.evaluate(doc,
						XPathConstants.NODESET);
				System.out.println(nodes);
				// for (int i = 0; i < nodes.getLength(); i++) {
				// System.out.println("name = " +
				// nodes.item(i).getFirstChild().getNodeValue());
				// }
				long end = System.currentTimeMillis();
				System.out.println("执行时间:" + (end - begin));
				Thread.sleep(1);
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
