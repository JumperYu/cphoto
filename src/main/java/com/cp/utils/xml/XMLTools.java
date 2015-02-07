package com.cp.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * xml工具 提供多种实现的用法
 * 
 * @author zengxm 2015年2月7日
 * 
 */
public class XMLTools {

	/**
	 * 获取DOM结构对象
	 * 
	 * @param filepath
	 *            文件绝对路径
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document parseDocument(String filepath)
			throws ParserConfigurationException, SAXException, IOException {
		return parseDocument(new FileInputStream(new File(filepath)));
	}

	/**
	 * 获取DOM结构对象
	 * 
	 * @param in
	 *            获取输入流
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document parseDocument(InputStream in)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(in);
		return doc;
	}

	/**
	 * 获取DOM结构对象
	 * 
	 * @param url
	 * @return
	 * @throws FileNotFoundException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static Document parseDocument(URL url) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException,
			URISyntaxException {
		return parseDocument(new FileInputStream(new File(url.toURI())));
	}

	/**
	 * 获取DOM结构对象
	 * 
	 * @param uri
	 * @return
	 * @throws FileNotFoundException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static Document parseDocument(URI uri) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException,
			URISyntaxException {
		return parseDocument(new FileInputStream(new File(uri)));
	}
	
	/**
	 * 打印根节里的所有节点内容
	 * 
	 * @param root
	 */
	public static void printDom(Element root) {
		if (root == null)
			return;
		NodeList collegeNodes = root.getChildNodes();
		if (collegeNodes == null)
			return;
		for (int i = 0; i < collegeNodes.getLength(); i++) {
			Node element = collegeNodes.item(i);
			if (element != null && element.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println(element.getNodeName() + ";"
						+ element.getFirstChild().getNodeValue());
			}
		}
	}

}
