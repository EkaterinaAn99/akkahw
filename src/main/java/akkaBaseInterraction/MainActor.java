package akkaBaseInterraction;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class MainActor extends AbstractBehavior<String> {

	public static Behavior<String> create() {
		return Behaviors.setup(MainActor::new);
	}


	private MainActor(ActorContext<String> context) {
		super(context);

	}
	@Override
	public Receive<String> createReceive() {
		return newReceiveBuilder()
				.onMessageEquals("start", this::onStart)
				.onMessageEquals("stop", this::onStop)
				.build();
	}

	private Behavior<String> onStop() {
		System.out.println("Главный актор закончил работу");
		return Behaviors.stopped();
	}

	private Behavior<String> onStart() {
		getContext().getLog().info("начал работу");
		ActorRef<String> refOne = getContext().spawn(ActorOne.create(), "ActorOne");
		refOne.tell("ActorOne");
		return this;
	}

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create(MainActor.create(), "hello");
		system.tell("start");
		system.terminate();
	}
}