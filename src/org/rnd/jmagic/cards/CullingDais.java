package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Culling Dais")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CullingDais extends Card
{
	public static final class CullingDaisAbility0 extends ActivatedAbility
	{
		public CullingDaisAbility0(GameState state)
		{
			super(state, "(T), Sacrifice a creature: Put a charge counter on Culling Dais.");
			this.costsTap = true;
			this.addCost(sacrificeACreature());
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Culling Dais."));
		}
	}

	public static final class CullingDaisAbility1 extends ActivatedAbility
	{
		public CullingDaisAbility1(GameState state)
		{
			super(state, "(1), Sacrifice Culling Dais: Draw a card for each charge counter on Culling Dais.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeThis("Culling Dais"));
			this.addEffect(drawCards(You.instance(), Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE)), "Draw a card for each charge counter on Culling Dais."));
		}
	}

	public CullingDais(GameState state)
	{
		super(state);

		// (T), Sacrifice a creature: Put a charge counter on Culling Dais.
		this.addAbility(new CullingDaisAbility0(state));

		// (1), Sacrifice Culling Dais: Draw a card for each charge counter on
		// Culling Dais.
		this.addAbility(new CullingDaisAbility1(state));
	}
}
