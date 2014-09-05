package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cryptborn Horror")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("1(B/R)(B/R)")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class CryptbornHorror extends Card
{
	public CryptbornHorror(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Cryptborn Horror enters the battlefield with X +1/+1 counters on it,
		// where X is the total life lost by your opponents this turn.
		SetGenerator X = Sum.instance(LifeLostThisTurn.instance(OpponentsOf.instance(You.instance())));
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), X, "X +1/+1 counters on it, where X is the total life lost by your opponents this turn", Counter.CounterType.PLUS_ONE_PLUS_ONE));

		state.ensureTracker(new LifeLostThisTurn.LifeTracker());
	}
}
