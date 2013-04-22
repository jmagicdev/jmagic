package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kor Skyfisher")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SOLDIER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KorSkyfisher extends Card
{
	public static final class Skyfishing extends EventTriggeredAbility
	{
		public Skyfishing(GameState state)
		{
			super(state, "When Kor Skyfisher enters the battlefield, return a permanent you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory bounce = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "return a permanent you control to its owner's hand.");
			bounce.parameters.put(EventType.Parameter.CAUSE, This.instance());
			bounce.parameters.put(EventType.Parameter.PLAYER, You.instance());
			bounce.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounce.parameters.put(EventType.Parameter.CHOICE, ControlledBy.instance(You.instance()));
			this.addEffect(bounce);
		}
	}

	public KorSkyfisher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Kor Skyfisher enters the battlefield, return a permanent you
		// control to its owner's hand.
		this.addAbility(new Skyfishing(state));
	}
}
