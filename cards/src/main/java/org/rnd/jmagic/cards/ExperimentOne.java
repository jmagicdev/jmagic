package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Experiment One")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.OOZE})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class ExperimentOne extends Card
{
	public static final class ExperimentOneAbility1 extends ActivatedAbility
	{
		public ExperimentOneAbility1(GameState state)
		{
			super(state, "Remove two +1/+1 counters from Experiment One: Regenerate Experiment One.");
			// Remove two +1/+1 counters from Experiment One
			this.addCost(removeCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Remove two +1/+1 counters from Experiment One"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Experiment One."));
		}
	}

	public ExperimentOne(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));

		// Remove two +1/+1 counters from Experiment One: Regenerate Experiment
		// One.
		this.addAbility(new ExperimentOneAbility1(state));
	}
}
