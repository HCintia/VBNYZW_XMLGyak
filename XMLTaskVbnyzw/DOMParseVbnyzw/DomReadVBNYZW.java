import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

public class DomReadVBNYZW {

    /*A met�dus bet�lt egy f�jlt, elemzi �s hib�t k�ld, ha nem tal�lja */
    public static void main(String[] args) {
        File xmlFile = new File("XMLvbnyzw.xml");
        Document doc = introduceFile(xmlFile);

        if (doc == null) {
            System.out.println("A dokumentum nem tal�lhat�");
            System.exit(-1);
        } else {
            processDocument(doc);
        }
    }

    /*Form�tum elk�sz�t�se*/
    private static void processDocument(Document doc) {
        doc.getDocumentElement().normalize();
        System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        System.out.println("<" + doc.getDocumentElement().getNodeName() +
                " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XSDvbnyzw.xsd\">");

        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        String indent = "";
        listData(nodeList, indent);
        System.out.println("</" + doc.getDocumentElement().getNodeName() + ">");

        /*Dokumentum f�jlba ment�se.*/
        saveDocumentToFile(doc, "XMLvbnyzwReadThisFile.xml");
    }

    private static void saveDocumentToFile(Document doc, String fileName) {
        /*A TransformerFactory elemz�s helyett a f�jlba �r*/
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");

            transformer.transform(new DOMSource(doc), new StreamResult(fileName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*A DocumentBuilderFactory az XML f�jlt olvassa �s �talak�tja*/
    public static Document introduceFile(File xmlFile) {
        Document doc = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            doc = dbBuilder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    /*K�perny�re ki�r�s*/
    private static void listData(NodeList nodeList, String indent) {

        indent += "\t";

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && !node.getTextContent().trim().isEmpty()) {
                    processElementNode((Element) node, indent);
                } else if (node instanceof Text) {
                    String value = node.getNodeValue().trim();
                    if (!value.isEmpty()) {
                        System.out.println(indent + node.getTextContent());
                    }
                }
            }
        }
    }

    /*Elemek feldolgoz�sa �s �r�sra k�szen �ll�sa */
    private static void processElementNode(Element element, String indent) {
        System.out.print(indent + "<" + element.getNodeName());
        if (element.hasAttributes()) {
            processAttributes(element);
        }
        System.out.println(">");

        NodeList nodeList_new = element.getChildNodes();
        listData(nodeList_new, indent);
        System.out.println(indent + "</" + element.getNodeName() + ">");
    }
    /*Attrib�tumok feldolgoz�sa �s �r�sra k�szen �ll�sa */
    private static void processAttributes(Element element) {
        NamedNodeMap attributes = element.getAttributes();
        for (int k = 0; k < attributes.getLength(); k++) {
            Node attribute = attributes.item(k);
            System.out.print(" " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
        }
    }
}
