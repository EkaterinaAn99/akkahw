package akkaBaseInterraction;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class FirstBD extends AbstractBehavior<String> {

    private String messageSecondBD = "AnswerToBD2";
    ActorRef refSecondBD = getContext().spawn(SecondBD.create(), "SecondBD2");
    public static Behavior<String> create() {
        return Behaviors.setup(context -> new FirstBD(context));
    }


    private int greetingCounter;

    private FirstBD(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("BD2toBD1", this::onGreeted)
                .onMessageEquals("FirstBDProof", this::receiveTrue)
                .build();
    }

    private Behavior<String> receiveTrue() {
        getContext().getLog().info("БД1 получила сообщение от ActorOne");
        return this;
    }

    private Behavior<String> onGreeted() {
        getContext().getLog().info("отправил {}", messageSecondBD);
        refSecondBD.tell(messageSecondBD);
        return this;
    }

    /*public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create(FirstBD.create(), "Test");
        system.tell("BD1toBD2");
        system.terminate();
    }*/
}