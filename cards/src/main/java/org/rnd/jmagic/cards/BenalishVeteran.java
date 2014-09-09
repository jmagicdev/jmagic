package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Benalish Veteran")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class BenalishVeteran extends Card
{
	public static final class BenalishVeteranAbility0 extends EventTriggeredAbility
	{
		public BenalishVeteranAbility0(GameState state)
		{
			super(state, "Whenever Benalish Veteran attacks, it gets +1/+1 until end of turn.");
			this.addPattern(whenThisAttacks());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Benalish Veteran gets +1/+1 until end of turn."));
		}
	}

	public BenalishVeteran(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Benalish Veteran attacks, it gets +1/+1 until end of turn.
		this.addAbility(new BenalishVeteranAbility0(state));
	}
}
