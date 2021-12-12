package akkaBaseInterraction;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class ActorFour extends AbstractBehavior<String> {

    private int counter = 0;
    private int checkerForFunction = 0;
    private String messageSecondBD = "BD2";
    private String messageActorThree = "ActorThreeEnd";
    ActorRef refThree = getContext().spawn(ActorThree.create(), "ActorThree2");
    ActorRef refSecondBD = getContext().spawn(SecondBD.create(), "SecondBD");



    public static Behavior<String> create() {
        return Behaviors.setup(context -> new ActorFour(context));
    }

    private int greetingCounter;

    private ActorFour(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("ActorFour", this::onGreeted)
                .build();
    }

    private Behavior<String> onGreeted() {
        getContext().getLog().info("отправил {}", messageSecondBD);
        refSecondBD.tell(messageSecondBD);
        getContext().getLog().info("отправил {}", messageActorThree);
        refThree.tell(messageActorThree);
        return this;
    }

}