package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Regal Force")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4GGG")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class RegalForce extends Card
{
	public static final class RegalForceAbility0 extends EventTriggeredAbility
	{
		public RegalForceAbility0(GameState state)
		{
			super(state, "When Regal Force enters the battlefield, draw a card for each green creature you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator amount = Count.instance(Intersect.instance(HasColor.instance(Color.GREEN), CREATURES_YOU_CONTROL));
			this.addEffect(drawCards(You.instance(), amount, "Draw a card for each green creature you control."));
		}
	}

	public RegalForce(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// When Regal Force enters the battlefield, draw a card for each green
		// creature you control.
		this.addAbility(new RegalForceAbility0(state));
	}
}
