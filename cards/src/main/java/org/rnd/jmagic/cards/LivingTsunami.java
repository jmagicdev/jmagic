package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Living Tsunami")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class LivingTsunami extends Card
{
	public static final class EatLands extends EventTriggeredAbility
	{
		public EatLands(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Living Tsunami unless you return a land you control to its owner's hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory sacrifice = sacrificeThis("Living Tsunami");

			SetGenerator yourLands = Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance()));

			EventFactory bounce = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return a land you control to its owner's hand");
			bounce.parameters.put(EventType.Parameter.CAUSE, This.instance());
			bounce.parameters.put(EventType.Parameter.PLAYER, You.instance());
			bounce.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounce.parameters.put(EventType.Parameter.CHOICE, yourLands);

			this.addEffect(unless(You.instance(), sacrifice, bounce, "Sacrifice Living Tsunami unless you return a land you control to its owner's hand."));
		}
	}

	public LivingTsunami(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, sacrifice Living Tsunami unless you
		// return a land you control to its owner's hand.
		this.addAbility(new EatLands(state));
	}
}
