package net.zfp.util;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class StringToXml {

	public void stringToXml(String xmlString) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(xmlString)));
		File file = new File("/mnt/file.xml");
		FileWriter writer = new FileWriter(file);
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		// create string from xml tree

		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(document);
		trans.transform(source, result);

	}

	public void readXml(Document doc) {
		doc.getDocumentElement().normalize();
		System.out.println("Root element " + doc.getDocumentElement().getNodeName());
		NodeList nodeLst = doc.getElementsByTagName("PanelString");

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element fstElmnt = (Element) fstNode;
				NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("StringId");
				Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
				System.out.println("text: " + fstNmElmnt.getNodeValue());
				NodeList fstNm = fstNmElmnt.getChildNodes();
				System.out.println("id : " + ((Node) fstNm.item(0)).getNodeValue());
				NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("StringPower");
				Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
				NodeList lstNm = lstNmElmnt.getChildNodes();
				System.out.println("power : " + ((Node) lstNm.item(0)).getNodeValue());
				
			}

		}
	}
}