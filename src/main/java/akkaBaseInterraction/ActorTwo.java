package akkaBaseInterraction;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class ActorTwo extends AbstractBehavior<String> {

	private int counter = 0;

	private String messageActorThree = "ActorThreeCont";
	ActorRef refActorThree = getContext().spawn(ActorThree.create(), "ActorThree");
	public static Behavior<String> create() {
		return Behaviors.setup(context -> new ActorTwo(context));
	}

	private int greetingCounter;

	private ActorTwo(ActorContext<String> context) {
		super(context);
	}

	@Override
	public Receive<String> createReceive() {
		return newReceiveBuilder()
				.onMessageEquals("ActorTwo", this::onGreeted)
				.build();
	}

	private Behavior<String> onGreeted() {
		getContext().getLog().info("отправил {}", messageActorThree );
		refActorThree.tell(messageActorThree);
		return this;


		}

	}
