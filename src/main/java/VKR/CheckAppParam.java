package VKR;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import akka.actor.typed.javadsl.AbstractBehavior;

public class CheckAppParam extends AbstractBehavior<CheckNetwork.Greeted> {
    public static Behavior<CheckNetwork.Greeted> create() {
        return Behaviors.setup(CheckAppParam::new);
    }

    private CheckAppParam(ActorContext<CheckNetwork.Greeted> context) {
        super(context);
    }

    @Override
    public Receive<CheckNetwork.Greeted> createReceive() {
        return newReceiveBuilder().onMessage(CheckNetwork.Greeted.class, this::onGreeted).build();
    }

    private Behavior<CheckNetwork.Greeted> onGreeted(CheckNetwork.Greeted message) throws InterruptedException {
        getContext().getLog().info("Выполнить проверки уровня приложений для установки {} на {}", message.program, message.typeOS);
        if (message.program.equals(CommanCommands.JAVA.getTitle())) {
            ActorRef<CheckNetwork.Greeted> java = getContext().spawn(Java.create(), ("Java" + message.whom));
            java.tell(new CheckNetwork.Greeted(message.whom, message.program, message.typeOS));
         }else if (message.program.equals(CommanCommands.MPI.getTitle())) {
            ActorRef<CheckNetwork.Greeted> mpi = getContext().spawn(MPI.create(), ("MPI" + message.whom));
            mpi.tell(new CheckNetwork.Greeted(message.whom, message.program, message.typeOS));
         }else if (message.program.equals(CommanCommands.PYTHON.getTitle())) {
            ActorRef<CheckNetwork.Greeted> python = getContext().spawn(Python.create(), ("Python" + message.whom));
            python.tell(new CheckNetwork.Greeted(message.whom, message.program, message.typeOS));
         }else {
            getContext().getLog().info("При проверке ОС произошла ошибка --getPath {}", message.typeOS);
        }
        return this;
     }

}

