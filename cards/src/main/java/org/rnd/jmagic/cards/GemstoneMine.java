package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gemstone Mine")
@Types({Type.LAND})
@ColorIdentity({})
public final class GemstoneMine extends Card
{
	public static final class GemstoneMineAbility1 extends ActivatedAbility
	{
		public GemstoneMineAbility1(GameState state)
		{
			super(state, "(T), Remove a mining counter from Gemstone Mine: Add one mana of any color to your mana pool. If there are no mining counters on Gemstone Mine, sacrifice it.");
			this.costsTap = true;
			this.addCost(removeCountersFromThis(1, Counter.CounterType.MINING, "Gemstone Mine"));

			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));

			SetGenerator counters = CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.MINING);
			SetGenerator depleted = Intersect.instance(numberGenerator(0), Count.instance(counters));

			EventFactory sacrificeIfDepleted = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If there are no mining counters on Gemstone Mine, sacrifice it.");
			sacrificeIfDepleted.parameters.put(EventType.Parameter.IF, depleted);
			sacrificeIfDepleted.parameters.put(EventType.Parameter.THEN, Identity.instance(sacrificeThis("Gemstone Mine")));
			this.addEffect(sacrificeIfDepleted);
		}
	}

	public GemstoneMine(GameState state)
	{
		super(state);

		// Gemstone Mine enters the battlefield with three mining counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 3, Counter.CounterType.MINING));

		// (T), Remove a mining counter from Gemstone Mine: Add one mana of any
		// color to your mana pool. If there are no mining counters on Gemstone
		// Mine, sacrifice it.
		this.addAbility(new GemstoneMineAbility1(state));
	}
}
