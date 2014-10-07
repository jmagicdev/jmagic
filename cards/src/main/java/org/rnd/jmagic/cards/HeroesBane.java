package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heroes' Bane")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class HeroesBane extends Card
{
	public static final class HeroesBaneAbility1 extends ActivatedAbility
	{
		public HeroesBaneAbility1(GameState state)
		{
			super(state, "(2)(G)(G): Put X +1/+1 counters on Heroes' Bane, where X is its power.");
			this.setManaCost(new ManaPool("(2)(G)(G)"));

			SetGenerator X = PowerOf.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(putCounters(X, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put X +1/+1 counters on Heroes' Bane, where X is its power."));

		}
	}

	public HeroesBane(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Heroes' Bane enters the battlefield with four +1/+1 counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 4, Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// (2)(G)(G): Put X +1/+1 counters on Heroes' Bane, where X is its
		// power.
		this.addAbility(new HeroesBaneAbility1(state));
	}
}
