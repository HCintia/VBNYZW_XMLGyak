import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.*;
import java.io.*;

import java.util.Set;
import java.util.HashSet;
//generált

public class DOMQuery {
	 private final XPathFactory xPathFactory;
	    private final XPath xPath;

	    private final Set<String> uniqueCourses;
	    private final Set<String> uniqueTeachers;

	    private final String outputFileName = "vbnyzworarend.xml"; // Change this as needed

	    public DOMQuery() {
	        this.xPathFactory = XPathFactory.newInstance();
	        this.xPath = xPathFactory.newXPath();
	        this.uniqueCourses = new HashSet<>();
	        this.uniqueTeachers = new HashSet<>();
	    }

	    public static void main(String[] args) {
	    	DOMQuery domQuery = new DOMQuery();
	        Document xmlDoc = domQuery.loadXMLDocument("vbnyzworarend.xml");

	        System.out.println("List of Unique Courses:");
	        domQuery.listUniqueCourses(xmlDoc);

	        System.out.println("\nFirst Instance of 'ora':");
	        domQuery.printFirstInstance(xmlDoc);

	        System.out.println("\nList of Unique Course Teachers:");
	        domQuery.listUniqueCourseTeachers(xmlDoc);

	        System.out.println("\nComplex Query Result:");
	        domQuery.complexQuery(xmlDoc, "Dr. Bozsik Sándor");
	    }

	    private Document loadXMLDocument(String fileName) {
	        try {
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            return builder.parse(fileName);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    private void listUniqueCourses(Document xmlDoc) {
	        try {
	            XPathExpression expr = xPath.compile("//targy");
	            NodeList courses = (NodeList) expr.evaluate(xmlDoc, XPathConstants.NODESET);

	            for (int i = 0; i < courses.getLength(); i++) {
	                String courseName = courses.item(i).getTextContent();
	                if (uniqueCourses.add(courseName)) {
	                    System.out.println(courseName);
	                }
	            }
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }
	    }

	    private void printFirstInstance(Document xmlDoc) {
	        try {
	            XPathExpression expr = xPath.compile("//ora[1]");
	            Node firstInstance = (Node) expr.evaluate(xmlDoc, XPathConstants.NODE);
	            if (firstInstance != null) {
	                writeToFile(outputFileName, firstInstance);
	                System.out.println("First instance written to " + outputFileName);
	            } else {
	                System.out.println("No 'ora' element found.");
	            }
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }
	    }

	    private void listUniqueCourseTeachers(Document xmlDoc) {
	        try {
	            XPathExpression expr = xPath.compile("//oktato");
	            NodeList teachers = (NodeList) expr.evaluate(xmlDoc, XPathConstants.NODESET);

	            for (int i = 0; i < teachers.getLength(); i++) {
	                String teacherName = teachers.item(i).getTextContent();
	                if (uniqueTeachers.add(teacherName)) {
	                    System.out.println(teacherName);
	                }
	            }
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }
	    }

	    private void complexQuery(Document xmlDoc, String instructorName) {
	        try {
	            // Find all courses taught by a specific instructor
	            XPathExpression expr = xPath.compile("//ora[oktato='" + instructorName + "']/targy");
	            NodeList coursesByInstructor = (NodeList) expr.evaluate(xmlDoc, XPathConstants.NODESET);
	            for (int i = 0; i < coursesByInstructor.getLength(); i++) {
	                System.out.println(coursesByInstructor.item(i).getTextContent());
	            }
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }
	    }

	    private void writeToFile(String fileName, Node contentNode) {
	        try (OutputStream outputStream = new FileOutputStream(fileName)) {
	            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
	            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(outputStream);

	            // Write the content node to the XML file
	            writeNode(xmlStreamWriter, contentNode);

	            xmlStreamWriter.close();
	        } catch (IOException | javax.xml.stream.XMLStreamException e) {
	            e.printStackTrace();
	        }
	    }

	    private void writeNode(XMLStreamWriter xmlStreamWriter, Node node) throws javax.xml.stream.XMLStreamException {
	        switch (node.getNodeType()) {
	            case Node.ELEMENT_NODE:
	                xmlStreamWriter.writeStartElement(node.getNodeName());
	                NodeList children = node.getChildNodes();
	                for (int i = 0; i < children.getLength(); i++) {
	                    writeNode(xmlStreamWriter, children.item(i));
	                }
	                xmlStreamWriter.writeEndElement();
	                break;
	            case Node.TEXT_NODE:
	                xmlStreamWriter.writeCharacters(node.getNodeValue());
	                break;
	        }
	    }
}
