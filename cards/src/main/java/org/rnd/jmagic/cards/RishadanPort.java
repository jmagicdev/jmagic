package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rishadan Port")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.RARE)})
@ColorIdentity({})
public final class RishadanPort extends Card
{
	public static final class TapTargetLand extends ActivatedAbility
	{
		public TapTargetLand(GameState state)
		{
			super(state, "(1), (T): Tap target land.");
			this.setManaCost(new ManaPool("1"));
			this.costsTap = true;

			Target target = this.addTarget(LandPermanents.instance(), "target land");
			this.addEffect(tap(targetedBy(target), "Tap target land."));
		}
	}

	public RishadanPort(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1), (T): Tap target land.
		this.addAbility(new TapTargetLand(state));
	}
}
