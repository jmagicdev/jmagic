package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Shrine of Loyal Legions")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ShrineofLoyalLegions extends Card
{
	public static final class ShrineofLoyalLegionsAbility0 extends EventTriggeredAbility
	{
		public ShrineofLoyalLegionsAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep or whenever you cast a white spell, put a charge counter on Shrine of Loyal Legions.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SimpleEventPattern castABlueSpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			castABlueSpell.put(EventType.Parameter.PLAYER, You.instance());
			castABlueSpell.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.WHITE)));
			this.addPattern(castABlueSpell);

			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Shrine of Loyal Legions."));
		}
	}

	public static final class ShrineofLoyalLegionsAbility1 extends ActivatedAbility
	{
		public ShrineofLoyalLegionsAbility1(GameState state)
		{
			super(state, "(3), (T), Sacrifice Shrine of Loyal Legions: Put a 1/1 colorless Myr artifact creature token onto the battlefield for each charge counter on Shrine of Loyal Legions.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Shrine of Loyal Legions"));

			SetGenerator counters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));

			CreateTokensFactory tokens = new CreateTokensFactory(counters, numberGenerator(1), numberGenerator(1), "Put a 1/1 colorless Myr artifact creature token onto the battlefield for each charge counter on Shrine of Loyal Legions.");
			tokens.setSubTypes(SubType.MYR);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public ShrineofLoyalLegions(GameState state)
	{
		super(state);

		// At the beginning of your upkeep or whenever you cast a white spell,
		// put a charge counter on Shrine of Loyal Legions.
		this.addAbility(new ShrineofLoyalLegionsAbility0(state));

		// (3), (T), Sacrifice Shrine of Loyal Legions: Put a 1/1 colorless Myr
		// artifact creature token onto the battlefield for each charge counter
		// on Shrine of Loyal Legions.
		this.addAbility(new ShrineofLoyalLegionsAbility1(state));
	}
}
