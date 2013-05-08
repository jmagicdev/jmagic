package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stampeding Wildebeests")
@Types({Type.CREATURE})
@SubTypes({SubType.ANTELOPE, SubType.BEAST})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.VISIONS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class StampedingWildebeests extends Card
{
	public static final class BounceGreen extends EventTriggeredAbility
	{
		public BounceGreen(GameState state)
		{
			super(state, "At the beginning of your upkeep, return a green creature you control to its owner's hand.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator controller = ControllerOf.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator choices = Intersect.instance(Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(controller)), HasColor.instance(Color.GREEN));

			EventType.ParameterMap bounceParams = new EventType.ParameterMap();
			bounceParams.put(EventType.Parameter.CAUSE, This.instance());
			bounceParams.put(EventType.Parameter.PLAYER, controller);
			bounceParams.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounceParams.put(EventType.Parameter.CHOICE, choices);
			this.addEffect(new EventFactory(EventType.PUT_INTO_HAND_CHOICE, bounceParams, "Return a green creature you control to its owner's hand"));
		}
	}

	public StampedingWildebeests(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		this.addAbility(new BounceGreen(state));
	}
}
