package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sinew Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SinewSliver extends Card
{
	public static final class SliverPump extends StaticAbility
	{
		public SliverPump(GameState state)
		{
			super(state, "All Sliver creatures get +1/+1.");

			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.SLIVER)), +1, +1));
		}
	}

	public SinewSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// All Sliver creatures get +1/+1.
		this.addAbility(new SliverPump(state));
	}
}
