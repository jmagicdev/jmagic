package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deranged Outcast")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.HUMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class DerangedOutcast extends Card
{
	public static final class DerangedOutcastAbility0 extends ActivatedAbility
	{
		public DerangedOutcastAbility0(GameState state)
		{
			super(state, "(1)(G), Sacrifice a Human: Put two +1/+1 counters on target creature.");
			this.setManaCost(new ManaPool("(1)(G)"));
			// Sacrifice a Human
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.HUMAN), "Sacrifice a Human"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put two +1/+1 counters on target creature."));
		}
	}

	public DerangedOutcast(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (1)(G), Sacrifice a Human: Put two +1/+1 counters on target creature.
		this.addAbility(new DerangedOutcastAbility0(state));
	}
}
