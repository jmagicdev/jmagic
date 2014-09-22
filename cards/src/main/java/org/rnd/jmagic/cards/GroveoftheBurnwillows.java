package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grove of the Burnwillows")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.GREEN})
public final class GroveoftheBurnwillows extends Card
{
	public static final class TapForROrG extends org.rnd.jmagic.abilities.TapForMana
	{
		public TapForROrG(GameState state)
		{
			super(state, "(RG)");
			this.setName(getName() + " Each opponent gains 1 life.");

			this.addEffect(gainLife(OpponentsOf.instance(You.instance()), 1, "Each opponent gains 1 life."));
		}
	}

	public GroveoftheBurnwillows(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T): Add (R) or (G) to your mana pool. Each opponent gains 1 life.
		this.addAbility(new TapForROrG(state));
	}
}
