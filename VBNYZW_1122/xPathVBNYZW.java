package xpathvbnyzw;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class xPathVBNYZW {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse("studentVBNYZW.xml");
        document.getDocumentElement().normalize();

        XPath xPath = XPathFactory.newInstance().newXPath();

         String expression = "/class/student"; //1. �sszes student elem, amely a class gyereke
        // String expression = "//student[@id=02]"; //2. student elem, amely rendelkezik "id" attrib�tummal �s az �rt�ke "02"
        // String expression = "//student"; //3. �sszes student elem
        // String expression = "/class/student[2]"; //4. m�sodik student elem, ami a class root element gyereke 
        // String expression = "/class/student[last()]"; //5. utols� student elem, ami a class root element gyereke
        // String expression = "/class/student[last()-1]"; //6. utols� el�tti student elem, ami a class root element gyereke
        // String expression = "/class/student[position()<=2]"; //7. els� k�t student elem, ami a root element gyerekei
        // String expression = "/class/*"; //8. Class root element �sszes gyerek eleme
        // String expression = "/class/student[@*]"; //9. �sszes student elem, amely 1 b�rmilyen attrib�tummal rendelkezik
        // String expression = "//*"; //10. a dokumentum �sszes eleme
        // String expression = "/class/student[20<kor]"; //11. class root element, �sszes student eleme, ahol a kor>20
        // String expression = "/class/student/keresztnev | /class/student/vezeteknev"; //�sszes student elem, �sszes keresztnev or vezeteknev csom�pontja

        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("Current node: " + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
                Element element = (Element) node;

                System.out.println("Student ID: " + element.getAttribute("id"));
                System.out.println("Their first name: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
                System.out.println("Their surname: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("Their nickname: " + element.getElementsByTagName("becenev").item(0).getTextContent());
                System.out.println("Their age: " + element.getElementsByTagName("kor").item(0).getTextContent());
            }
        }
    }

}
