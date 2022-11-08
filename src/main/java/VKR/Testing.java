package VKR;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Testing extends AbstractBehavior<CheckNetwork.Greeted> {

    public static Behavior<CheckNetwork.Greeted> create() {
        return Behaviors.setup(Testing::new);
    }

    private Testing(ActorContext<CheckNetwork.Greeted> context) {
        super(context);
    }

    @Override
    public Receive<CheckNetwork.Greeted> createReceive() {
        return newReceiveBuilder().onMessage(CheckNetwork.Greeted.class, this::onGreeted).build();
    }

    private Behavior<CheckNetwork.Greeted> onGreeted(CheckNetwork.Greeted message) throws InterruptedException {
        getContext().getLog().info("Запуск тестирования", message.program);
        if (message.typeOS.equals("Windows")) {
            if (message.program.equals("Java")) {
                getContext().getLog().info("-->{}", CommandsWindows.JAVATEST.getTitle());
            } else if (message.program.equals("MPI")) {
                getContext().getLog().info("-->{}", CommandsWindows.MPITEST.getTitle());
            } else {
                getContext().getLog().info("-->{}", CommandsWindows.PYTHONTEST.getTitle());
            }
        }else{
            if (message.program.equals("Java")) {
                getContext().getLog().info("-->{}", CommandsLinux.JAVATEST.getTitle());
            } else if (message.program.equals("MPI")) {
                getContext().getLog().info("-->{}", CommandsLinux.MPITEST.getTitle());
            } else {
                getContext().getLog().info("-->{}", CommandsLinux.PYTHONTEST.getTitle());
            }

        }return Behaviors.stopped();
    }
}

