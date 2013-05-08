package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trigon of Corruption")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class TrigonofCorruption extends Card
{
	public static final class TrigonofCorruptionAbility1 extends ActivatedAbility
	{
		public TrigonofCorruptionAbility1(GameState state)
		{
			super(state, "(B)(B), (T): Put a charge counter on Trigon of Corruption.");
			this.setManaCost(new ManaPool("(B)(B)"));
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Trigon of Corruption"));
		}
	}

	public static final class TrigonofCorruptionAbility2 extends ActivatedAbility
	{
		public TrigonofCorruptionAbility2(GameState state)
		{
			super(state, "(2), (T), Remove a charge counter from Trigon of Corruption: Put a -1/-1 counter on target creature.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Trigon of Corruptions"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature."));
		}
	}

	public TrigonofCorruption(GameState state)
	{
		super(state);

		// Trigon of Corruption enters the battlefield with three charge
		// counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Trigon of Corruption", 3, Counter.CounterType.CHARGE));

		// (B)(B), (T): Put a charge counter on Trigon of Corruption.
		this.addAbility(new TrigonofCorruptionAbility1(state));

		// (2), (T), Remove a charge counter from Trigon of Corruption: Put a
		// -1/-1 counter on target creature.
		this.addAbility(new TrigonofCorruptionAbility2(state));
	}
}
