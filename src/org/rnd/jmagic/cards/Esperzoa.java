package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Esperzoa")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.JELLYFISH})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Esperzoa extends Card
{
	public static final class RescueJellyfish extends EventTriggeredAbility
	{
		public RescueJellyfish(GameState state)
		{
			super(state, "At the beginning of your upkeep, return an artifact you control to its owner's hand.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory factory = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return an artifact you control to its owner's hand");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasType.instance(Type.ARTIFACT), ControlledBy.instance(You.instance())));
			this.addEffect(factory);
		}
	}

	public Esperzoa(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new RescueJellyfish(state));
	}
}
