package VKR;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder
                .parse(new File("C:\\Users\\Katerina\\IdeaProjects\\akkahw\\src\\main\\java\\actorForDiploma\\param.xml"));
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());
        NodeList nList = document.getElementsByTagName("PC");
        System.out.println("============================");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Print each employee's detail
                Element eElement = (Element) node;
                String program = eElement.getElementsByTagName("program").item(0).getTextContent();
                System.out.println("Number : " + eElement.getAttribute("number"));
                System.out.println("IP : " + eElement.getElementsByTagName("IP").item(0).getTextContent());
                System.out.println("JOB : " + eElement.getElementsByTagName("program").item(0).getTextContent());
                System.out.println("OS : " + eElement.getElementsByTagName("OS").item(0).getTextContent());
                if (program.equals("Java")){
                    System.out.println("Yes");
                }
            }
        }



    }



}
