package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shifting Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Legions.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ShiftingSliver extends Card
{
	public final static class SliverFear extends StaticAbility
	{
		public SliverFear(GameState state)
		{
			super(state, "Slivers can't be blocked except by Slivers.");

			SetGenerator slivers = HasSubType.instance(SubType.SLIVER);
			SetGenerator attackingSlivers = Intersect.instance(Attacking.instance(), slivers);
			SetGenerator blockingAttackingSlivers = Blocking.instance(attackingSlivers);
			SetGenerator illegalBlockers = RelativeComplement.instance(blockingAttackingSlivers, slivers);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(illegalBlockers));
			this.addEffectPart(part);
		}
	}

	public ShiftingSliver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Slivers can't be blocked except by Slivers.
		this.addAbility(new SliverFear(state));
	}
}
