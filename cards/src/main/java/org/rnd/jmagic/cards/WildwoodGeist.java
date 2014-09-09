package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wildwood Geist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class WildwoodGeist extends Card
{
	public static final class WildwoodGeistAbility0 extends StaticAbility
	{
		public WildwoodGeistAbility0(GameState state)
		{
			super(state, "Wildwood Geist gets +2/+2 as long as it's your turn.");
			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));
			this.canApply = Both.instance(this.canApply, Intersect.instance(CurrentTurn.instance(), TurnOf.instance(You.instance())));
		}
	}

	public WildwoodGeist(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Wildwood Geist gets +2/+2 as long as it's your turn.
		this.addAbility(new WildwoodGeistAbility0(state));
	}
}
