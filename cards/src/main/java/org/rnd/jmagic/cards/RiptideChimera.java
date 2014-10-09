package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Riptide Chimera")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.CHIMERA})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class RiptideChimera extends Card
{
	public static final class RiptideChimeraAbility1 extends EventTriggeredAbility
	{
		public RiptideChimeraAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, return an enchantment you control to its owner's hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory factory = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return an enchantment you control to its owner's hand");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasType.instance(Type.ENCHANTMENT), ControlledBy.instance(You.instance())));
			this.addEffect(factory);
		}
	}

	public RiptideChimera(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, return an enchantment you control to
		// its owner's hand.
		this.addAbility(new RiptideChimeraAbility1(state));
	}
}
