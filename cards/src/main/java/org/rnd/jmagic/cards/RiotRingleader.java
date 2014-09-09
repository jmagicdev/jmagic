package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Riot Ringleader")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class RiotRingleader extends Card
{
	public static final class RiotRingleaderAbility0 extends EventTriggeredAbility
	{
		public RiotRingleaderAbility0(GameState state)
		{
			super(state, "Whenever Riot Ringleader attacks, Human creatures you control get +1/+0 until end of turn.");
			this.addPattern(whenThisAttacks());
			this.addEffect(ptChangeUntilEndOfTurn(Intersect.instance(HasSubType.instance(SubType.HUMAN), CREATURES_YOU_CONTROL), +1, +0, "Human creatures you control get +1/+0 until end of turn."));
		}
	}

	public RiotRingleader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Riot Ringleader attacks, Human creatures you control get
		// +1/+0 until end of turn.
		this.addAbility(new RiotRingleaderAbility0(state));
	}
}
