package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Borderland Marauder")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class BorderlandMarauder extends Card
{
	public static final class BorderlandMarauderAbility0 extends EventTriggeredAbility
	{
		public BorderlandMarauderAbility0(GameState state)
		{
			super(state, "Whenever Borderland Marauder attacks, it gets +2/+0 until end of turn.");
			this.addPattern(whenThisAttacks());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Borderland Marauder it gets +2/+0 until end of turn."));
		}
	}

	public BorderlandMarauder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Whenever Borderland Marauder attacks, it gets +2/+0 until end of
		// turn.
		this.addAbility(new BorderlandMarauderAbility0(state));
	}
}
