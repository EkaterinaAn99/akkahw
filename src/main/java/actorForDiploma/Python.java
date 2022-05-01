package actorForDiploma;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.Date;

public class Python extends AbstractBehavior<Q0N.Greeted> {

    public static Behavior<Q0N.Greeted> create() {
        return Behaviors.setup(Python::new);
    }

    private Python(ActorContext<Q0N.Greeted> context) {
        super(context);
    }

    @Override
    public Receive<Q0N.Greeted> createReceive() {
        return newReceiveBuilder().onMessage(Q0N.Greeted.class, this::onGreeted).build();
    }

    private Behavior<Q0N.Greeted> onGreeted(Q0N.Greeted message) throws InterruptedException {

        getContext().getLog().info("Установлен пакет {} на {}", message.program, message.whom);
        Thread.sleep(1000);
        getContext().getLog().info("Проверен path {} на {}", message.program, message.whom);
        getContext().getLog().info("Отправлены обновленные файлы конфигурации {} на {}", message.program, message.whom);
        return Behaviors.stopped();
    }
}
