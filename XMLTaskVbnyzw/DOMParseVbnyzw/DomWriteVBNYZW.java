import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class DomWriteVBNYZW {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element rootElement = document.createElement("tancegyesulet");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XSDvbnyzw.xsd");
            document.appendChild(rootElement);

            /*Elemek feltöltése, feldolgozása*/
            /*Egyesület*/
            processEgyesulet(document, rootElement, "1", 10000, "Ködmön", "Majoros", "Miskolc", "3524", 1993);
            processEgyesulet(document, rootElement, "2", 12000, "Lazúr", "Hóvirág", "Miskolc", "3525", 1997);
            processEgyesulet(document, rootElement, "3", 15000, "Savaria", "Király", "Szombathely", "3800", 2000);
            /*Táncos*/
            processTancos(document, rootElement, "001", "A", "1991.01.01", "Kiss Szimonetta", "latin");
            processTancos(document, rootElement, "002", "B", "1994.04.16", "Fenyves Béla", "latin");
            processTancos(document, rootElement, "003", "C", "1986.05.23", "Fekete Nóra", "standard");
            processTancos(document, rootElement, "004", "C", "1997.08.02", "Kiss Zoltán", "standard");
            processTancos(document, rootElement, "005", "A", "2000.01.22", "Kovács Ádám", "standard");
            processTancos(document, rootElement, "006", "A", "1991.12.11", "Mezei Hanga", "latin");
            /*Verseny*/
            processVerseny(document, rootElement, "01", "2024.04.27", "Tiszaújváros Sportcsarnok", "Malek Andrea");
            processVerseny(document, rootElement, "02", "2024.06.06", "Sátoraljaújhely Sportcsarnok", "Zsámboki Marcell");
            /*Szurkoló*/
            processSzurkolo(document, rootElement, "0001", "Győri Eszter", "1978.05.24");
            processSzurkolo(document, rootElement, "0002", "Kovács Margit", "1990.04.25");
            processSzurkolo(document, rootElement, "0003", "Hajdu Péter", "1991.02.07");
            processSzurkolo(document, rootElement, "0004", "Boros Balázs", "1990.06.17");
            processSzurkolo(document, rootElement, "0005", "Horváth Gergely", "1967.12.21");
            processSzurkolo(document, rootElement, "0006", "Németh Márk", "1974.08.19");
            /*Produkció*/
            processProdukcio(document, rootElement, "0001", "Breakaway", 2, "Karácsony", Arrays.asList("keringő"));
            processProdukcio(document, rootElement, "0002", "Zumba", 16, "Karibi tánc", Arrays.asList("latin", "zumba"));
            processProdukcio(document, rootElement, "0003", "Paso soundtrac", 6, "Torreádor", Arrays.asList("pasodoble"));
            processProdukcio(document, rootElement, "0004", "Moulin Rouge", 2, "Roxanne", Arrays.asList("tango"));
            processProdukcio(document, rootElement, "0005", "Fall in love", 1, "Szerelem", Arrays.asList("rumba"));
            processProdukcio(document, rootElement, "0006", "Mika Grace Kelly", 10, "Lollipop", Arrays.asList("cha cha cha"));


            printDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Deklaráció, azokat az elemeket tartalmazza, amiket a programnak el kell készítenie*/
    /*Egyesület*/
    private static void processEgyesulet(Document document, Element rootElement, String ekod,
            int havidij, String nev, String utca, String varos, String iranyitoszam, int alev) {
        Element egy = document.createElement("egyesulet");
        egy.setAttribute("ekod", ekod);

        Element havidijE = createElement(document, "havidij", String.valueOf(havidij));
        egy.appendChild(havidijE);

        Element nevE = createElement(document, "nev", nev);
        egy.appendChild(nevE);

        Element cimE = document.createElement("cim");
        Element utcaE = createElement(document, "utca", utca);
        Element varosE = createElement(document, "varos", varos);
        Element iranyitoszamE = createElement(document, "iranyitoszam", iranyitoszam);

        cimE.appendChild(utcaE);
        cimE.appendChild(varosE);
        cimE.appendChild(iranyitoszamE);

        egy.appendChild(cimE);
        rootElement.appendChild(egy);
    }

    /*Táncos*/

    private static void processTancos(Document document, Element rootElement, String tkod, String korcsoport, String t_szuldat, String t_nev, String t_stilus) {
        Element tanc = document.createElement("tancos");
        tanc.setAttribute("tkod", tkod);

        Element korcsoportE = createElement(document, "korcsoport", korcsoport);
        tanc.appendChild(korcsoportE);

        Element t_szuldatE = createElement(document, "t_szuldat", t_szuldat);
        tanc.appendChild(t_szuldatE);

        Element t_nevE = createElement(document, "t_nev", t_nev);
        tanc.appendChild(t_nevE);

        Element t_stilusE = createElement(document, "t_stilus", t_stilus);
        tanc.appendChild(t_stilusE);

        rootElement.appendChild(tanc);
    }

    /*Verseny*/

    private static void processVerseny(Document document, Element rootElement, String vkod, String idopont, String helyszin, String szervezo) {
        Element vers = document.createElement("verseny");
        vers.setAttribute("vkod", vkod);

        Element idopontE = createElement(document, "idopont", idopont);
        vers.appendChild(idopontE);

        Element helyszinE = createElement(document, "helyszin", helyszin);
        vers.appendChild(helyszinE);

        Element szervezoE = createElement(document, "szervezo", szervezo);
        vers.appendChild(szervezoE);

        rootElement.appendChild(vers);
    }

    /*Szurkoló*/

    private static void processSzurkolo(Document document, Element rootElement, String szkod, String sz_nev, String sz_szuldat) {
        Element szurk = document.createElement("szurkolo");
        szurk.setAttribute("szkod", szkod);

        Element sz_nevE = createElement(document, "sz_nev", sz_nev);
        szurk.appendChild(sz_nevE);

        Element sz_szuldatE = createElement(document, "sz_szuldat", sz_szuldat);
        szurk.appendChild(sz_szuldatE);

        rootElement.appendChild(szurk);
    }

    /*Produkció*/

    private static void processProdukcio(Document document, Element rootElement, String pkod, String zene, int letszam, String p_nev, List<String> p_stiluslista) {
        Element prod = document.createElement("produkcio");
        prod.setAttribute("pkod", pkod);

        Element zeneE = createElement(document, "zene", zene);
        prod.appendChild(zeneE);

	    Element letszamE = createElement(document, "letszam", String.valueOf(letszam));
        prod.appendChild(letszamE);

        Element p_nevE = createElement(document, "p_nev", p_nev);
        prod.appendChild(p_nevE);

        Element p_stilusE = document.createElement("p_stilus");
	for (String p : p_stiluslista){
		Element temp = createElement(document, "p_stilus", p);
		p_stilusE.appendChild(temp);
	}
        prod.appendChild(p_stilusE);

        rootElement.appendChild(prod);
    }

    /*Tánccsoport (Táncsport szövetség rövidítve)*/

    private static void processTanccsoport(Document document, Element rootElement, String egy_idgkulcs, String prod_idgkulcs, String vers_idgkulcs, String tanc_idgkulcs) {
        Element tanccsoport = document.createElement("tanccsoport");
        tanccsoport.setAttribute("egy_idgkulcs", egy_idgkulcs);
        tanccsoport.setAttribute("prod_idgkulcs", prod_idgkulcs);
        tanccsoport.setAttribute("vers_idgkulcs", vers_idgkulcs);
	tanccsoport.setAttribute("tanc_idgkulcs", tanc_idgkulcs);

        rootElement.appendChild(tanccsoport);
    }

    /*Nevezés*/

    private static void processNevez(Document document, Element rootElement, String nkod, String n_v_idgkulcs, int nevdij) {
        Element nevez = document.createElement("nevez");
        nevez.setAttribute("nkod", nkod);

        Element nevdijE = createElement(document, "nevdij", String.valueOf(nevdij));
	nevez.appendChild(nevdijE);

	nevez.setAttribute("n_v_idgkulcs", n_v_idgkulcs);

        rootElement.appendChild(nevez);
    }


    /*Elem létrehozása*/

    private static Element createElement(Document document, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        return element;
    }

    /* Kimenet létrehozása */

    private static void printDocument(Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            /*Létrehozza az XMLvbnyzw.xml nevű fájlt.*/
            transformer.transform(new DOMSource(document), new StreamResult("XMLvbnyzw.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
