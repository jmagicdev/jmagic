package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Telekinetic Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class TelekineticSliver extends Card
{
	@Name("\"(T): Tap target permanent.\"")
	public static final class SliverTap extends ActivatedAbility
	{
		public SliverTap(GameState state)
		{
			super(state, "(T): Tap target permanent.");

			this.costsTap = true;

			Target target = this.addTarget(Permanents.instance(), "target permanent");

			this.addEffect(tap(targetedBy(target), "Tap target permanent."));
		}
	}

	public TelekineticSliver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// All Slivers have "(T): Tap target permanent."
		this.addAbility(new org.rnd.jmagic.abilities.AllSliversHave(state, SliverTap.class, "All Slivers have \"(T): Tap target permanent.\""));
	}
}
