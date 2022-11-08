package VKR;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class CheckNetwork extends AbstractBehavior<CheckNetwork.Greet> {

    public static final class Greet {
        public final String whom;
        public final String program;
        //public final ActorRef<Greeted> replyTo;
        public  final String typeOS;

        public Greet(String whom, String program, String typeOS) {
            this.whom = whom;
            this.program = program;
            //this.replyTo = replyTo;
            this.typeOS = typeOS;
        }
    }

    public static final class Greeted {
        public final String whom;
        public final String program;
        public  final String typeOS;

        public Greeted(String whom, String program, String typeOS) {
            this.whom = whom;
            this.program = program;
            this.typeOS = typeOS;
        }
    }

    public static Behavior<Greet> create() {
        return Behaviors.setup(CheckNetwork::new);
    }

    private CheckNetwork(ActorContext<Greet> context) {
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
        getContext().getLog().info("Выполнить проверку сети для {}", command.whom);
        ActorRef<CheckNetwork.Greeted> replyTo = getContext().spawn(CheckAppParam.create(), ("CheckAppParam" + command.whom));
        if (PingIP.runSystemCommand("192.168.0.1") == true) {
            getContext().getLog().info("-->{} {}", CommanCommands.CHECKNET.getTitle(), command.whom);
            getContext().getLog().info("-->Вычислительная машина {} доступна: {}", command.whom, PingIP.runSystemCommand("192.168.0.1"));//заплатка IP
            replyTo.tell(new Greeted(command.whom, command.program, command.typeOS));
            return this;
        }
        else {
            getContext().getLog().info("Error {}!", command.whom);
            return Behaviors.stopped();
        }
    }
}