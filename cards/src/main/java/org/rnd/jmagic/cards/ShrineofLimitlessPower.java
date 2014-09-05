package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Shrine of Limitless Power")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ShrineofLimitlessPower extends Card
{
	public static final class ShrineofLimitlessPowerAbility0 extends EventTriggeredAbility
	{
		public ShrineofLimitlessPowerAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep or whenever you cast a black spell, put a charge counter on Shrine of Limitless Power.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SimpleEventPattern castABlueSpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			castABlueSpell.put(EventType.Parameter.PLAYER, You.instance());
			castABlueSpell.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.BLACK)));
			this.addPattern(castABlueSpell);

			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Shrine of Limitless Power."));
		}
	}

	public static final class ShrineofLimitlessPowerAbility1 extends ActivatedAbility
	{
		public ShrineofLimitlessPowerAbility1(GameState state)
		{
			super(state, "(4), (T), Sacrifice Shrine of Limitless Power: Target player discards a card for each charge counter on Shrine of Limitless Power.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Shrine of Limitless Power"));

			SetGenerator counters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(discardCards(target, counters, "Target player discards a card for each charge counter on Shrine of Limitless Power."));
		}
	}

	public ShrineofLimitlessPower(GameState state)
	{
		super(state);

		// At the beginning of your upkeep or whenever you cast a black spell,
		// put a charge counter on Shrine of Limitless Power.
		this.addAbility(new ShrineofLimitlessPowerAbility0(state));

		// (4), (T), Sacrifice Shrine of Limitless Power: Target player discards
		// a card for each charge counter on Shrine of Limitless Power.
		this.addAbility(new ShrineofLimitlessPowerAbility1(state));
	}
}
