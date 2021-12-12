package akkaBaseInterraction;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class ActorThree extends AbstractBehavior<String> {

    private String messageActorFour = "ActorFour";
    private String messageTheEnd = "TheEnd";
    ActorRef refActorFour = getContext().spawn(ActorFour.create(), "ActorFour");
    public static Behavior<String> create() {
        return Behaviors.setup(context -> new ActorThree(context));
    }

    private ActorThree(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("ActorThreeEnd", this::endFalse)
                .onMessageEquals("ActorThreeCont", this::actorThreeTrue)
                .build();
    }

    private Behavior<String> actorThreeTrue() {
        getContext().getLog().info("отправил {}", messageActorFour);
        refActorFour.tell(messageActorFour);
        return this;
    }

    private Behavior<String> endFalse() {
        getContext().getLog().info("ActorThree получил сообщение от ActorFour. Жду окончания работы БД2");
        return this;
    }
}

