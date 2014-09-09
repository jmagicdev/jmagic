package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Leyline Phantom")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class LeylinePhantom extends Card
{
	public static final class LeylinePhantomAbility0 extends EventTriggeredAbility
	{
		public LeylinePhantomAbility0(GameState state)
		{
			super(state, "When Leyline Phantom deals combat damage, return it to its owner's hand.");
			this.addPattern(whenDealsCombatDamage(ABILITY_SOURCE_OF_THIS));
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return it to its owner's hand."));
		}
	}

	public LeylinePhantom(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// When Leyline Phantom deals combat damage, return it to its owner's
		// hand. (Return it only if it survived combat.)
		this.addAbility(new LeylinePhantomAbility0(state));
	}
}
