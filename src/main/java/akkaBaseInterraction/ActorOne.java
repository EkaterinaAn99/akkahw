package akkaBaseInterraction;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class ActorOne extends AbstractBehavior<String> {

	private int counter = 0;
	private String messageBD = "FirstBDProof";
	private String messageActorTwo = "ActorTwo";
	ActorRef<String> refTwo = getContext().spawn(ActorTwo.create(), "ActorTwo");
	ActorRef<String> refFirstBD = getContext().spawn(FirstBD.create(), "FirstBD");

	public static Behavior<String> create() {
		return Behaviors.setup(ActorOne::new);
	}

	private ActorOne(ActorContext<String> context) {
		super(context);
	}

	public Receive<String> createReceive() {
		return newReceiveBuilder()
				.onMessageEquals("ActorOne", this::onGreet)
				.build();
	}

	private Behavior<String> onGreet() {
		getContext().getLog().info("отправил {}", messageBD);
		refFirstBD.tell(messageBD);
		getContext().getLog().info("отправил {}", messageActorTwo);
		refTwo.tell(messageActorTwo);
		return this;
	}

}
