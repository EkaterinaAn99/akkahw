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
        System.out.println(PingIP.runSystemCommand("192.168.0.1"));



    }



}
