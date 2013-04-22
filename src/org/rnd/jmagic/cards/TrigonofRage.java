package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trigon of Rage")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class TrigonofRage extends Card
{
	public static final class TrigonofRageAbility1 extends ActivatedAbility
	{
		public TrigonofRageAbility1(GameState state)
		{
			super(state, "(R)(R), (T): Put a charge counter on Trigon of Rage.");
			this.setManaCost(new ManaPool("(R)(R)"));
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Trigon of Rage"));
		}
	}

	public static final class TrigonofRageAbility2 extends ActivatedAbility
	{
		public TrigonofRageAbility2(GameState state)
		{
			super(state, "(2), (T), Remove a charge counter from Trigon of Rage: Target creature gets +3/+0 until end of turn.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Trigon of Rage"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(createFloatingEffect("Target creature gets +3/+0 until end of turn.", modifyPowerAndToughness(target, +3, +0)));
		}
	}

	public TrigonofRage(GameState state)
	{
		super(state);

		// Trigon of Rage enters the battlefield with three charge counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Trigon of Rage", 3, Counter.CounterType.CHARGE));

		// (R)(R), (T): Put a charge counter on Trigon of Rage.
		this.addAbility(new TrigonofRageAbility1(state));

		// (2), (T), Remove a charge counter from Trigon of Rage: Target
		// creature gets +3/+0 until end of turn.
		this.addAbility(new TrigonofRageAbility2(state));
	}
}
