package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Markov Blademaster")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VAMPIRE})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class MarkovBlademaster extends Card
{
	public static final class MarkovBlademasterAbility1 extends EventTriggeredAbility
	{
		public MarkovBlademasterAbility1(GameState state)
		{
			super(state, "Whenever Markov Blademaster deals combat damage to a player, put a +1/+1 counter on it.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on it."));
		}
	}

	public MarkovBlademaster(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Double strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));

		// Whenever Markov Blademaster deals combat damage to a player, put a
		// +1/+1 counter on it.
		this.addAbility(new MarkovBlademasterAbility1(state));
	}
}
