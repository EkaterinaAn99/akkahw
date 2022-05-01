package actorForDiploma;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Q0N extends AbstractBehavior<Q0N.Greet> {

    public static final class Greet {
        public final String whom;
        public final String program;
        public final ActorRef<Greeted> replyTo;

        public Greet(String whom, String program, ActorRef<Greeted> replyTo) {
            this.whom = whom;
            this.program = program;
            this.replyTo = replyTo;
        }
    }

    public static final class Greeted {
        public final String whom;
        public final String program;
        public final ActorRef<Greet> from;

        public Greeted(String whom, String program, ActorRef<Greet> from) {
            this.whom = whom;
            this.program = program;
            this.from = from;
        }
    }

    public static Behavior<Greet> create() {
        return Behaviors.setup(Q0N::new);
    }

    private Q0N(ActorContext<Greet> context) {
        super(context);
    }

    @Override
    public Receive<Greet> createReceive() {
        return newReceiveBuilder()
                .onMessage(Greet.class, this::onGreet)
                .build();
    }

    private Behavior<Greet> onGreet(Greet command) throws InterruptedException {

        boolean test = true;
        if (test == true) {
            getContext().getLog().info("Установлено соединение с {} для {}", command.whom, command.program);
            getContext().getLog().info("Определен тип ОС {} для настройки {}",  command.whom, command.program);
            command.replyTo.tell(new Greeted(command.whom, command.program, getContext().getSelf()));
            return this;
        }
        else {
            getContext().getLog().info("Error {}!", command.whom);
            return Behaviors.stopped();
        }
    }
}