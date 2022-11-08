package VKR;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
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

public class QSF extends AbstractBehavior<QSF.SetIP> {

    public static class SetIP {
        public final String name;
        public final String program;
        public final String typeOS;

    public SetIP(String name, String program, String typeOS) {
        this.name = name;
        this.program = program;
        this.typeOS = typeOS;
    }
}

    public static Behavior<SetIP> create() {
        return Behaviors.setup(QSF::new);
    }

    private QSF(ActorContext<SetIP> context) {
        super(context);

    }

    @Override
    public Receive<SetIP> createReceive() {
        return newReceiveBuilder().onMessage(SetIP.class, this::onStart).build();
    }

    private Behavior<SetIP> onStart(SetIP command) {
        ActorRef<CheckNetwork.Greet> greeter = getContext().spawn(CheckNetwork.create(), ("QSF" + command.name + command.program + command.typeOS));
        greeter.tell(new CheckNetwork.Greet(command.name, command.program, command.typeOS));
        return this;

    }

    //-------------------------------------------------------------------
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        final ActorSystem<QSF.SetIP> system =  ActorSystem.create(QSF.create(), "MainActor");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder
                .parse(new File("C:\\Users\\Katerina\\IdeaProjects\\akkahw\\src\\main\\java\\VKR\\param.xml"));
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        NodeList nList = document.getElementsByTagName("PC");
        System.out.println("Parameters:");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                System.out.println("Number = " + eElement.getAttribute("number")
                        + "; IP = " + eElement.getElementsByTagName("IP").item(0).getTextContent()
                        + "; Program = " + eElement.getElementsByTagName("program").item(0).getTextContent()
                        + "; OS = " + eElement.getElementsByTagName("OS").item(0).getTextContent());

                /*system.tell(new QSF.SetIP((String) eElement.getElementsByTagName("IP").item(0).getTextContent(),
                        (String) eElement.getElementsByTagName("program").item(0).getTextContent(),
                        (String) eElement.getElementsByTagName("OS").item(0).getTextContent()));*/
                String IP = eElement.getElementsByTagName("IP").item(0).getTextContent();
                String program = eElement.getElementsByTagName("program").item(0).getTextContent();
                String OS = eElement.getElementsByTagName("OS").item(0).getTextContent();

                system.tell(new SetIP(IP, program, OS));
                System.out.println("==============================================================");
            }
        }
        system.terminate();
    }
}