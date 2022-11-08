package VKR;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class MPI extends AbstractBehavior<CheckNetwork.Greeted> {
    //ActorRef<CheckNetwork.Greeted> test = getContext().spawn(Testing.create(), "Testing");

    public static Behavior<CheckNetwork.Greeted> create() {
        return Behaviors.setup(MPI::new);
    }

    private MPI(ActorContext<CheckNetwork.Greeted> context) {
        super(context);
    }

    @Override
    public Receive<CheckNetwork.Greeted> createReceive() {
        return newReceiveBuilder().onMessage(CheckNetwork.Greeted.class, this::onGreeted).build();
    }

    private Behavior<CheckNetwork.Greeted> onGreeted(CheckNetwork.Greeted message) throws InterruptedException {
        getContext().getLog().info("Запуск установки на {}", message.typeOS);
        ActorRef<CheckNetwork.Greeted> test = getContext().spawn(Testing.create(), message.whom);
        if (message.typeOS == CommanCommands.WINDOWS.getTitle()) {
            getContext().getLog().info("-->{}", CommandsWindows.MPIINSTALL.getTitle());
            test.tell(new CheckNetwork.Greeted(message.whom, message.program, message.typeOS));

        }else{
            getContext().getLog().info("-->{}", CommandsLinux.MPIINSTALL.getTitle());
            test.tell(new CheckNetwork.Greeted(message.whom, message.program, message.typeOS));

           }
        return this;
    }
}