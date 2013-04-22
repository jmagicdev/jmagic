package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Shrine of Boundless Growth")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ShrineofBoundlessGrowth extends Card
{
	public static final class ShrineofBoundlessGrowthAbility0 extends EventTriggeredAbility
	{
		public ShrineofBoundlessGrowthAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep or whenever you cast a green spell, put a charge counter on Shrine of Boundless Growth.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SimpleEventPattern castABlueSpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			castABlueSpell.put(EventType.Parameter.PLAYER, You.instance());
			castABlueSpell.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.GREEN)));
			this.addPattern(castABlueSpell);

			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Shrine of Boundless Growth."));
		}
	}

	public static final class ShrineofBoundlessGrowthAbility1 extends ActivatedAbility
	{
		public ShrineofBoundlessGrowthAbility1(GameState state)
		{
			super(state, "(T), Sacrifice Shrine of Boundless Growth: Add (1) to your mana pool for each charge counter on Shrine of Boundless Growth.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Shrine of Boundless Growth"));

			SetGenerator counters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add (1) to your mana pool for each charge counter on Shrine of Boundless Growth.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("(1)")));
			addMana.parameters.put(EventType.Parameter.NUMBER, counters);
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(addMana);
		}
	}

	public ShrineofBoundlessGrowth(GameState state)
	{
		super(state);

		// At the beginning of your upkeep or whenever you cast a green spell,
		// put a charge counter on Shrine of Boundless Growth.
		this.addAbility(new ShrineofBoundlessGrowthAbility0(state));

		// (T), Sacrifice Shrine of Boundless Growth: Add (1) to your mana pool
		// for each charge counter on Shrine of Boundless Growth.
		this.addAbility(new ShrineofBoundlessGrowthAbility1(state));
	}
}
