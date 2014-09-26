package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Kragma Butcher")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.WARRIOR})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class KragmaButcher extends Card
{
	public static final class KragmaButcherAbility0 extends EventTriggeredAbility
	{
		public KragmaButcherAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Kragma Butcher becomes untapped, it gets +2/+0 until end of turn.");
			this.addPattern(inspired());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Kragma Butcher gets +2/+0 until end of turn."));
		}
	}

	public KragmaButcher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Inspired \u2014 Whenever Kragma Butcher becomes untapped, it gets
		// +2/+0 until end of turn.
		this.addAbility(new KragmaButcherAbility0(state));
	}
}
