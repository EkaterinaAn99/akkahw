package actorForDiploma;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class QSF extends AbstractBehavior<QSF.SetIP> {

    public static class SetIP {
        public final String name;
        public final String program;

    public SetIP(String name, String program) {

        this.name = name;
        this.program = program;
    }
}

    public static Behavior<SetIP> create() {
        return Behaviors.setup(QSF::new);
    }

    private final ActorRef<Q0N.Greet> greeter;

    private QSF(ActorContext<SetIP> context) {
        super(context);
        greeter = context.spawn(Q0N.create(), "greeter");
    }

    @Override
    public Receive<SetIP> createReceive() {
        return newReceiveBuilder().onMessage(SetIP.class, this::onStart).build();
    }

    private Behavior<SetIP> onStart(SetIP command) {
        if (command.program == "Java") {
            ActorRef<Q0N.Greeted> replyTo = getContext().spawn(Java.create(), command.name);
            greeter.tell(new Q0N.Greet(command.name, command.program, replyTo));
            return this;
        } else if (command.program == "MPI"){
            ActorRef<Q0N.Greeted> replyTo = getContext().spawn(MPI.create(), command.name);
            greeter.tell(new Q0N.Greet(command.name, command.program, replyTo));
            return this;
        }else if (command.program == "Python"){
            ActorRef<Q0N.Greeted> replyTo = getContext().spawn(Python.create(), command.name);
            greeter.tell(new Q0N.Greet(command.name, command.program, replyTo));
            return this;
        }else {
            return this;
        }
    }

    //-------------------------------------------------------------------
    public static void main(String[] args) {
        final ActorSystem<QSF.SetIP> system =  ActorSystem.create(QSF.create(), "MainActor");
        String[] IP = new String[] {"192.168.0.1", "192.168.0.2", "192.168.0.3", "192.168.0.4","192.168.0.5"};

        /*for (int i = 0; i < IP.length; i++){
            system.tell(new QSF.SetIP(IP[i], "java"));
        }*/
        system.tell(new QSF.SetIP(IP[0], "MPI"));
        system.tell(new QSF.SetIP(IP[1], "Java"));
        system.tell(new QSF.SetIP(IP[2], "Python"));
        system.tell(new QSF.SetIP(IP[3], "Java"));

        system.terminate();

        System.out.println("Test");
    }
}