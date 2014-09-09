package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oblivion Stone")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class OblivionStone extends Card
{
	public static final class OblivionStoneAbility0 extends ActivatedAbility
	{
		public OblivionStoneAbility0(GameState state)
		{
			super(state, "(4), (T): Put a fate counter on target permanent.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

			this.addEffect(putCounters(1, Counter.CounterType.FATE, target, "Put a fate counter on target permanent."));
		}
	}

	public static final class OblivionStoneAbility1 extends ActivatedAbility
	{
		public OblivionStoneAbility1(GameState state)
		{
			super(state, "(5), (T), Sacrifice Oblivion Stone: Destroy each nonland permanent without a fate counter on it, then remove all fate counters from all permanents.");
			this.setManaCost(new ManaPool("(5)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Oblivion Stone"));

			this.addEffect(destroy(RelativeComplement.instance(Permanents.instance(), Union.instance(HasCounterOfType.instance(Counter.CounterType.FATE), LandPermanents.instance())), "Destroy each nonland permanent without a fate counter on it."));

			this.addEffect(removeCounters(null, Counter.CounterType.FATE, Permanents.instance(), "Remove all fate counters from all permanents."));
		}
	}

	public OblivionStone(GameState state)
	{
		super(state);

		// (4), (T): Put a fate counter on target permanent.
		this.addAbility(new OblivionStoneAbility0(state));

		// (5), (T), Sacrifice Oblivion Stone: Destroy each nonland permanent
		// without a fate counter on it, then remove all fate counters from all
		// permanents.
		this.addAbility(new OblivionStoneAbility1(state));
	}
}
