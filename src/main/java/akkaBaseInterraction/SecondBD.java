package akkaBaseInterraction;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class SecondBD extends AbstractBehavior<String> {

    private String messageFirstBD = "BD2toBD1";
    private String messageToMain = "stop";
    ActorRef refFirstBD = getContext().spawn(FirstBD.create(), "FirstBD");
    ActorRef mainActor = getContext().spawn(MainActor.create(), "MainActorEnd");

    public static Behavior<String> create() {
        return Behaviors.setup(context -> new SecondBD(context));
    }

    private SecondBD(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("BD2", this::onGreeted)
                .onMessageEquals("AnswerToBD2", this::answerToBD1)
                .build();
    }

    private Behavior<String> answerToBD1() {
        getContext().getLog().info("БД2 получила ответ от БД1. Работа закончена");
        mainActor.tell(messageToMain);
        return this;
    }

    private Behavior<String> onGreeted() {
        getContext().getLog().info("отправил {}", messageFirstBD);
        refFirstBD.tell(messageFirstBD);
        return this;
    }

}