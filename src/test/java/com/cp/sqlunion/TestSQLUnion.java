package com.cp.sqlunion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.cp.utils.xml.SQLPool;

/**
 * 高仿MyBatis XML 配置SQL
 * 
 * 
 * @author zengxm 2015年2月7日
 * 
 */
public class TestSQLUnion {

	private static String xml_uri = "com/cp/subject/service/subject.xml";

	// --------------------------------- 测试W3C DOM API -------------------
	@Test
	public void testW3cDomApiRead() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			InputStream in = TestSQLUnion.class.getClassLoader()
					.getResourceAsStream(xml_uri);
			Document doc = builder.parse(in);
			// root <university>
			Element root = doc.getDocumentElement();
			if (root == null)
				return;
			// all college node
			NodeList collegeNodes = root.getChildNodes();
			if (collegeNodes == null)
				return;
			for (int i = 0; i < collegeNodes.getLength(); i++) {
				Node element = collegeNodes.item(i);
				if (element != null
						&& element.getNodeType() == Node.ELEMENT_NODE) {
					System.out.println(element.getNodeName() + ";"
							+ element.getFirstChild().getNodeValue());
					String sql = element.getFirstChild().getNodeValue();
					System.out.println(sql);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testW3cDomWrite() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			InputStream in = TestSQLUnion.class.getClassLoader()
					.getResourceAsStream(xml_uri);
			Document doc = builder.parse(in);
			Element root = doc.getDocumentElement();
			Element element = doc.createElement("sql");
			element.setAttribute("id", "find_order");
			root.appendChild(element);
			Text text = doc.createTextNode("123123");
			element.appendChild(text);
			System.out.println(TestSQLUnion.class.getClassLoader().getResource(
					xml_uri));
			// 将修改后的文档保存到文件
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transFormer = transFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			File file = new File("e:/test.xml");
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			StreamResult xmlResult = new StreamResult(out);
			transFormer.transform(domSource, xmlResult);
			System.out.println(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --------------------------------- 测试SAX -------------------
	// 纯SAX对于写操作无能为力
	@Test
	public void testSaxRead() {
		try {
			// 1.创建解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 2.得到解析器
			SAXParser parser = factory.newSAXParser();
			// 3.得到读取器
			InputStream in = TestSQLUnion.class.getClassLoader()
					.getResourceAsStream(xml_uri);
			parser.parse(in, new MyHandler());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------- 测试DOM4j -------------------
	public static void testDom4j() throws DocumentException {
		SAXReader reader = new SAXReader();
		InputStream in = TestSQLUnion.class.getClassLoader()
				.getResourceAsStream(xml_uri);
		org.dom4j.Document doc = reader.read(in);
		org.dom4j.Element root = doc.getRootElement();
		System.out.println(root.element("sql").asXML());
	}

	@Test
	public void testSQLPool() {
			long begin = System.currentTimeMillis();
			String sql = SQLPool.getSQL(TestSQLUnion.class.getClassLoader()
					.getResource(xml_uri), "find_subjects");
			long end = System.currentTimeMillis();
			System.out.println(end - begin);
			System.out.println(sql);
	}

	// 重写对自己感兴趣的事件处理方法
	class MyHandler extends DefaultHandler {

		@Override
		public InputSource resolveEntity(String publicId, String systemId)
				throws IOException, SAXException {
			return super.resolveEntity(publicId, systemId);
		}

		@Override
		public void notationDecl(String name, String publicId, String systemId)
				throws SAXException {
			super.notationDecl(name, publicId, systemId);
		}

		@Override
		public void unparsedEntityDecl(String name, String publicId,
				String systemId, String notationName) throws SAXException {
			super.unparsedEntityDecl(name, publicId, systemId, notationName);
		}

		@Override
		public void setDocumentLocator(Locator locator) {
			super.setDocumentLocator(locator);
		}

		@Override
		public void startDocument() throws SAXException {
			System.err.println("开始解析文档");
		}

		@Override
		public void endDocument() throws SAXException {
			System.err.println("解析结束");
		}

		@Override
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
			super.startPrefixMapping(prefix, uri);
		}

		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
			super.endPrefixMapping(prefix);
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			System.err.print("Element: " + qName + ", attr: ");
			print(attributes);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			System.out.println(new String(ch, start, length));
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
			super.ignorableWhitespace(ch, start, length);
		}

		@Override
		public void processingInstruction(String target, String data)
				throws SAXException {
			super.processingInstruction(target, data);
		}

		@Override
		public void skippedEntity(String name) throws SAXException {
			super.skippedEntity(name);
		}

		@Override
		public void warning(SAXParseException e) throws SAXException {
			super.warning(e);
		}

		@Override
		public void error(SAXParseException e) throws SAXException {
			super.error(e);
		}

		@Override
		public void fatalError(SAXParseException e) throws SAXException {
			super.fatalError(e);
		}

		private void print(Attributes attrs) {
			if (attrs == null)
				return;
			System.err.print("[");
			for (int i = 0; i < attrs.getLength(); i++) {
				System.err.print(attrs.getQName(i) + " = " + attrs.getValue(i));
				if (i != attrs.getLength() - 1) {
					System.err.print(", ");
				}
			}
			System.err.println("]");
		}
	}
}
