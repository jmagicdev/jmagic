package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grindclock")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class Grindclock extends Card
{
	public static final class GrindclockAbility0 extends ActivatedAbility
	{
		public GrindclockAbility0(GameState state)
		{
			super(state, "(T): Put a charge counter on Grindclock.");
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Grindclock."));
		}
	}

	public static final class GrindclockAbility1 extends ActivatedAbility
	{
		public GrindclockAbility1(GameState state)
		{
			super(state, "(T): Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of charge counters on Grindclock.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE)), "Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of charge counters on Grindclock."));
		}
	}

	public Grindclock(GameState state)
	{
		super(state);

		// (T): Put a charge counter on Grindclock.
		this.addAbility(new GrindclockAbility0(state));

		// (T): Target player puts the top X cards of his or her library into
		// his or her graveyard, where X is the number of charge counters on
		// Grindclock.
		this.addAbility(new GrindclockAbility1(state));
	}
}
