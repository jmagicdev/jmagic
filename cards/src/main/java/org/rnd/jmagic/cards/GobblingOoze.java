package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gobbling Ooze")
@Types({Type.CREATURE})
@SubTypes({SubType.OOZE})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class GobblingOoze extends Card
{
	public static final class GobblingOozeAbility0 extends ActivatedAbility
	{
		public GobblingOozeAbility0(GameState state)
		{
			super(state, "(G), Sacrifice another creature: Put a +1/+1 counter on Gobbling Ooze.");
			this.setManaCost(new ManaPool("(G)"));

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addCost(sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature"));

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Gobbling Ooze."));
		}
	}

	public GobblingOoze(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (G), Sacrifice another creature: Put a +1/+1 counter on Gobbling
		// Ooze.
		this.addAbility(new GobblingOozeAbility0(state));
	}
}
