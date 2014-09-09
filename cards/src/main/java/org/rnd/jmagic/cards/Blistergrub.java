package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blistergrub")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class Blistergrub extends Card
{
	public static final class BlistergrubAbility1 extends EventTriggeredAbility
	{
		public BlistergrubAbility1(GameState state)
		{
			super(state, "When Blistergrub dies, each opponent loses 2 life.");
			this.addPattern(whenThisDies());
			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 2, "Each opponent loses 2 life."));
		}
	}

	public Blistergrub(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Swampwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));

		// When Blistergrub dies, each opponent loses 2 life.
		this.addAbility(new BlistergrubAbility1(state));
	}
}
