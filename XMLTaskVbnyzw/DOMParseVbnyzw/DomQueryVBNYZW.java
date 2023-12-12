import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomQueryVBNYZW{
    public static void main(String[] args) {
        File xmlFile = new File("XMLvbnyzw.xml"); //XML fáj elérési útja
        Document doc = parseXmlFile(xmlFile);

        if (doc != null) {
            doc.getDocumentElement().normalize();

            int tancEgyesuletekSzama = tancEgyesuletekSzamaVarosban(doc, "Miskolc");
            System.out.println("Táncegyesületek száma Miskolcon: " + tancEgyesuletekSzama);

            listaNemEgyedulTancolok(doc);

            listaEgyesuletSzurkoloi(doc, "Ködmön");

            listaSavariaTancosai(doc, "1993");

            listCompetitionsInYear(doc, "2024");

        } else {
            System.out.println("The document is null");
            System.exit(-1);
        }
    }

    private static Document parseXmlFile(File xmlFile) {
        Document doc = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            doc = dbBuilder.parse(xmlFile);
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        return doc;
    }
    //Miskolci táncegyesületek száma
    private static int tancEgyesuletekSzamaVarosban(Document doc, String varos) {
        NodeList tancEgyesulet = doc.getDocumentElement().getElementsByTagName("egyesulet");
        int tancEgyesuletekSzama = 0;

        for (int i = 0; i < tancEgyesuletekSzama.getLength(); i++) {
            Element tancEgyesulet = (Element) tancEgyesulet.item(i);
            String egyesuletVaros = tancEgyesulet.getElementsByTagName("varos").item(0).getTextContent();

            if (egyesuletVaros.equals(varos)) {
                tancEgyesuletekSzama++;
            }
        }

        return tancEgyesuletekSzama;
    }

    //Azok a produkciók, amik nem szólók (2 vagy annál többen táncolják)
    private static void listaNemEgyedulTancolok(Document doc) {
        NodeList produk = doc.getDocumentElement().getElementsByTagName("produkcio");

        System.out.println("A nem szolo produkciók:");

        for (int i = 0; i < produk.getLength(); i++) {
            Element produk = (Element) produk.item(i);
            int tancosSzamolo = Integer.parseInt(produk.getElementsByTagName("letszam").item(0).getTextContent());

            if (tancosSzamolo > 2) {
                String ProdukcioNev = produk.getElementsByTagName("p_nev").item(0).getTextContent();
                System.out.println("Produkció: " + ProdukcioNev + ", Táncolók létszáma: " + tancosSzamolo);
            }
        }
    }

    //A Ködmönnek szurkolók
    private static void listaEgyesuletSzurkoloi(Document doc, String tancEgyesuletNev) {
        NodeList listaz = doc.getDocumentElement().getElementsByTagName("egyesulet");

        System.out.println("Az adott egyesület szurkolói:" + tancEgyesuletNev + ":");

        for (int i = 0; i < listaz.getLength(); i++) {
            Element tancStudio = (Element) listaz.item(i);
            String studioNev = tancStudio.getElementsByTagName("nev").item(0).getTextContent();

            if (studioNev.equals(tancEgyesuletNev)) {
                NodeList fanok = tancStudio.getElementsByTagName("szurkolo");

                for (int j = 0; j < fanok.getLength(); j++) {
                    Element fan = (Element) fanok.item(j);
                    String fanNev = fan.getElementsByTagName("sz_nev").item(0).getTextContent();
                    String fanSzuldat = fan.getElementsByTagName("sz_szuldat").item(0).getTextContent();

                    System.out.println("Szurkolo neve: " + fanNev + ", születési dátuma: " + fanSzuldat);
                }
            }
        }
    }

    //Savaria 1993.után született táncosai
    private static void listaSavariaTancosai(Document doc, String szulnap) {
        NodeList lista = doc.getElementsByTagName("tancos");

        System.out.println("Savaria táncosa, aki 1993. után ekkor született" + szulnap + ":");

        for (int i = 0; i < lista.getLength(); i++) {
            Element tancolo = (Element) lista.item(i);
            String tancoloStudio = getElementValueByTagName(tancolo, "egyesulet/nev");
            String tancoloNev = getElementValueByTagName(tancolo, "t_nev");
            String tancoloSzuldat = getElementValueByTagName(tancolo, "t_szuldat");

            String[] szuletEs = szulDate.split("\\.");
            String szulEv = szuletEs.length > 0 ? szuletEs[0] : "";

            if (tancoloStudio.equals("Savaria") && Integer.parseInt(szulEv) > Integer.parseInt(szulnap)) {
                System.out.println("Név: " + tancoloNev + ", születési dátum: " + szulDate);
            }
        }
    }

    private static String getElementValueByTagName(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    //2024-es versenyek listája
    private static void listaVersenyekEv(Document doc, String year) {
        NodeList versenyek = doc.getElementsByTagName("verseny");

        System.out.println("Versenyek az adott évben: " + year + ":");

        for (int i = 0; i < versenyek.getLength(); i++) {
            Element versen = (Element) versenyek.item(i);
            String versenyDatum = getElementValueByTagName(versen, "idopont");

            String[] datumResz = versenyDatum.split("\\.");
            String versenyEv = datumResz.length > 0 ? datumResz[0] : "";

            if (versenyEv.equals(year)) {
                String versenyHelyszin = getElementValueByTagName(versen, "helyszin");
                System.out.println("Dátum: " + versenyDatum + ", Helyszin: " + versenyHelyszin);
            }
        }
    }
}
