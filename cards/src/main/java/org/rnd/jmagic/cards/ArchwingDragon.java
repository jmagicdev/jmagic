package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Archwing Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class ArchwingDragon extends Card
{
	public static final class ArchwingDragonAbility1 extends EventTriggeredAbility
	{
		public ArchwingDragonAbility1(GameState state)
		{
			super(state, "At the beginning of the end step, return Archwing Dragon to its owner's hand.");
			this.addPattern(atTheBeginningOfTheEndStep());
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Archwing Dragon to its owner's hand."));
		}
	}

	public ArchwingDragon(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// At the beginning of the end step, return Archwing Dragon to its
		// owner's hand.
		this.addAbility(new ArchwingDragonAbility1(state));
	}
}
