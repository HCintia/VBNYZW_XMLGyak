import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DOMRead {
	public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse("orarendvbnyzw.xml");

            Element root = doc.getDocumentElement();
            NodeList oraList = root.getElementsByTagName("ora");

            for (int i = 0; i < oraList.getLength(); i++) {
                Node oraNode = oraList.item(i);
                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;
                    String id = oraElement.getAttribute("id");
                    String tipus = oraElement.getAttribute("tipus");

                    String targy = oraElement.getElementsByTagName("targy").item(0).getTextContent();
                    Element idopontElement = (Element) oraElement.getElementsByTagName("idopont").item(0);
                    String nap = idopontElement.getElementsByTagName("nap").item(0).getTextContent();
                    String tol = idopontElement.getElementsByTagName("tol").item(0).getTextContent();
                    String ig = idopontElement.getElementsByTagName("ig").item(0).getTextContent();

                    String helyszin = oraElement.getElementsByTagName("helyszin").item(0).getTextContent();
                    String oktato = oraElement.getElementsByTagName("oktato").item(0).getTextContent();
                    String szak = oraElement.getElementsByTagName("szak").item(0).getTextContent();

                    System.out.println("ID: " + id);
                    System.out.println("Típus: " + tipus);
                    System.out.println("Tárgy: " + targy);
                    System.out.println("Nap: " + nap);
                    System.out.println("Időpont: " + tol + " - " + ig);
                    System.out.println("Helyszín: " + helyszin);
                    System.out.println("Oktató: " + oktato);
                    System.out.println("Szak: " + szak);
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

