package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sigil of Distinction")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("X")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({})
public final class SigilofDistinction extends Card
{
	public static final class BoostCreature extends StaticAbility
	{
		public BoostCreature(GameState state)
		{
			super(state, "Equipped creature gets +1/+1 for each charge counter on Sigil of Distinction.");
			SetGenerator boost = Count.instance(CountersOn.instance(This.instance(), Counter.CounterType.CHARGE));
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), boost, boost));
		}
	}

	public SigilofDistinction(GameState state)
	{
		super(state);

		// Sigil of Distinction enters the battlefield with X charge counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), ValueOfX.instance(This.instance()), "X charge counters on it", Counter.CounterType.CHARGE));

		// Equipped creature gets +1/+1 for each charge counter on Sigil of
		// Distinction.
		this.addAbility(new BoostCreature(state));

		// Equip\u2014Remove a charge counter from Sigil of Distinction.
		EventFactory removeCounter = removeCountersFromThis(1, Counter.CounterType.CHARGE, this.getName());
		CostCollection equipCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Equip.COST_TYPE, removeCounter);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, equipCost));
	}
}
