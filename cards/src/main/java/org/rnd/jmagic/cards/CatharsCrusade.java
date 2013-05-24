package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cathars' Crusade")
@Types({Type.ENCHANTMENT})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class CatharsCrusade extends Card
{
	public static final class CatharsCrusadeAbility0 extends EventTriggeredAbility
	{
		public CatharsCrusadeAbility0(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, put a +1/+1 counter on each creature you control.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), You.instance(), false));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, CREATURES_YOU_CONTROL, "Put a +1/+1 counter on each creature you control."));
		}
	}

	public CatharsCrusade(GameState state)
	{
		super(state);

		// Whenever a creature enters the battlefield under your control, put a
		// +1/+1 counter on each creature you control.
		this.addAbility(new CatharsCrusadeAbility0(state));
	}
}
