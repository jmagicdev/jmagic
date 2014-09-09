package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ruthless Cullblade")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VAMPIRE})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class RuthlessCullblade extends Card
{
	public static final class Ruthless extends StaticAbility
	{
		public Ruthless(GameState state)
		{
			super(state, "Ruthless Cullblade gets +2/+1 as long as an opponent has 10 or less life.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +1));

			SetGenerator opponentIsDying = Intersect.instance(Between.instance(null, 10), LifeTotalOf.instance(OpponentsOf.instance(You.instance())));
			this.canApply = Both.instance(this.canApply, opponentIsDying);
		}
	}

	public RuthlessCullblade(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Ruthless Cullblade gets +2/+1 as long as an opponent has 10 or less
		// life.
		this.addAbility(new Ruthless(state));
	}
}
