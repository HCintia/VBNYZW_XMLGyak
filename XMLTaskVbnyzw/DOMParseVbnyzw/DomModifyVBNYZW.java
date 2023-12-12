import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DomModifyVBNYZW {
    public static void main(String[] args) {
        File xmlFile = new File("XMLvbnyzw.xml");
        Document doc = parseXmlFile(xmlFile);

        if (doc == null) {
            System.out.println("A dokumentum nem található");
            System.exit(-1);
        } else {
            doc.getDocumentElement().normalize();
        }

        modositLazurDij(doc.getDocumentElement(), "Lazúr", 15000);

        modisitTancosKorcsoport(doc.getDocumentElement(), "Kiss Szimonetta", "A", "B");

        // Kiírja a képernyőre a módosított XML-t
        printXml(doc);
    }

    private static Document parseXmlFile(File xmlFile) {
        Document doc = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            doc = dbBuilder.parse(xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doc;
    }


    //A Lazur egyesület havidíjának módosítása 15000-re
    private static void modositLazurDij(Element element, String egyesuletNev, int ujDij) {
        NodeList productLepes = element.getElementsByTagName("egyesulet");

        for (int i = 0; i < productLepes.getLength(); i++) {
            Element productElement = (Element) productLepes.item(i);
            String egyesuletNevXml = productElement.getElementsByTagName("nev").item(0).getTextContent();

            if (egyesuletNev.equals(egyesuletNevXml)) {

                Element dijElement = (Element) productElement.getElementsByTagName("havidij").item(0);
                dijElement.setTextContent(Integer.toString(ujDij));
                break;
            }
        }
    }

    //Kiss Szimonetta táncos korcsoportjának módosítása
    private static void modisitTancosKorcsoport(Element element, String tancosNev, String korCsoport, String ujKorCsoport) {
        NodeList tancosNodes = element.getElementsByTagName("tancos");

        for (int i = 0; i < tancosNodes.getLength(); i++) {
            Element tancosElement = (Element) tancosNodes.item(i);
            String tancosNevXml = tancosElement.getElementsByTagName("t_nev").item(0).getTextContent();
            String korCsoportXML = tancosElement.getElementsByTagName("korcsoport").item(0).getTextContent();

            if (tancosNev.equals(tancosNevXml) && korCsoport.equals(korCsoportXML)) {
                Element korCsoportElement = (Element) tancosElement.getElementsByTagName("korcsoport").item(0);
                korCsoportElement.setTextContent(ujKorCsoport);
                break;
            }
        }
    }

    private static void printXml(Document doc) {
        try {

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");


            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
