package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Decimator Web")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class DecimatorWeb extends Card
{
	public static final class DecimatorWebAbility0 extends ActivatedAbility
	{
		public DecimatorWebAbility0(GameState state)
		{
			super(state, "(4), (T): Target opponent loses 2 life, gets a poison counter, then puts the top six cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			this.addEffect(loseLife(target, 2, "Target opponent loses 2 life,"));
			this.addEffect(putCounters(1, Counter.CounterType.POISON, target, "gets a poison counter,"));
			this.addEffect(millCards(target, 6, "then puts the top six cards of his or her library into his or her graveyard."));
		}
	}

	public DecimatorWeb(GameState state)
	{
		super(state);

		// (4), (T): Target opponent loses 2 life, gets a poison counter, then
		// puts the top six cards of his or her library into his or her
		// graveyard.
		this.addAbility(new DecimatorWebAbility0(state));
	}
}
