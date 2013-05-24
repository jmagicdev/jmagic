package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Chronomaton")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Chronomaton extends Card
{
	public static final class ChronomatonAbility0 extends ActivatedAbility
	{
		public ChronomatonAbility0(GameState state)
		{
			super(state, "(1), (T): Put a +1/+1 counter on Chronomaton.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Chronomaton"));
		}
	}

	public Chronomaton(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1), (T): Put a +1/+1 counter on Chronomaton.
		this.addAbility(new ChronomatonAbility0(state));
	}
}
