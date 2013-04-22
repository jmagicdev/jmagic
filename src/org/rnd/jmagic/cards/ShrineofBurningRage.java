package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Shrine of Burning Rage")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ShrineofBurningRage extends Card
{
	public static final class ShrineofBurningRageAbility0 extends EventTriggeredAbility
	{
		public ShrineofBurningRageAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep or whenever you cast a red spell, put a charge counter on Shrine of Burning Rage.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SimpleEventPattern castABlueSpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			castABlueSpell.put(EventType.Parameter.PLAYER, You.instance());
			castABlueSpell.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.RED)));
			this.addPattern(castABlueSpell);

			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Shrine of Burning Rage."));
		}
	}

	public static final class ShrineofBurningRageAbility1 extends ActivatedAbility
	{
		public ShrineofBurningRageAbility1(GameState state)
		{
			super(state, "(3), (T), Sacrifice Shrine of Burning Rage: Shrine of Burning Rage deals damage equal to the number of charge counters on it to target creature or player.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Shrine of Burning Rage"));

			SetGenerator counters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(counters, target, "Shrine of Burning Rage deals damage equal to the number of charge counters on it to target creature or player."));
		}
	}

	public ShrineofBurningRage(GameState state)
	{
		super(state);

		// At the beginning of your upkeep or whenever you cast a red spell, put
		// a charge counter on Shrine of Burning Rage.
		this.addAbility(new ShrineofBurningRageAbility0(state));

		// (3), (T), Sacrifice Shrine of Burning Rage: Shrine of Burning Rage
		// deals damage equal to the number of charge counters on it to target
		// creature or player.
		this.addAbility(new ShrineofBurningRageAbility1(state));
	}
}
