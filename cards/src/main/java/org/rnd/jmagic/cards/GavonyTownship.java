package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gavony Township")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GavonyTownship extends Card
{
	public static final class GavonyTownshipAbility1 extends ActivatedAbility
	{
		public GavonyTownshipAbility1(GameState state)
		{
			super(state, "(2)(G)(W), (T): Put a +1/+1 counter on each creature you control.");
			this.setManaCost(new ManaPool("(2)(G)(W)"));
			this.costsTap = true;

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, CREATURES_YOU_CONTROL, "Put a +1/+1 counter on each creature you control."));
		}
	}

	public GavonyTownship(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (2)(G)(W), (T): Put a +1/+1 counter on each creature you control.
		this.addAbility(new GavonyTownshipAbility1(state));
	}
}
