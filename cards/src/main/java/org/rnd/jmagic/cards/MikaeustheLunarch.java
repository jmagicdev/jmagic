package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mikaeus, the Lunarch")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("XW")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.FTV_LEGENDS, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class MikaeustheLunarch extends Card
{
	public static final class MikaeustheLunarchAbility1 extends ActivatedAbility
	{
		public MikaeustheLunarchAbility1(GameState state)
		{
			super(state, "(T): Put a +1/+1 counter on Mikaeus.");
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Mikaeus."));
		}
	}

	public static final class MikaeustheLunarchAbility2 extends ActivatedAbility
	{
		public MikaeustheLunarchAbility2(GameState state)
		{
			super(state, "(T), Remove a +1/+1 counter from Mikaeus: Put a +1/+1 counter on each other creature you control.");
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Remove a +1/+1 counter from Mikaeus"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS), "Put a +1/+1 counter on Mikaeus."));
		}
	}

	public MikaeustheLunarch(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Mikaeus, the Lunarch enters the battlefield with X +1/+1 counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), ValueOfX.instance(This.instance()), "X +1/+1 counters on it", Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// (T): Put a +1/+1 counter on Mikaeus.
		this.addAbility(new MikaeustheLunarchAbility1(state));

		// (T), Remove a +1/+1 counter from Mikaeus: Put a +1/+1 counter on each
		// other creature you control.
		this.addAbility(new MikaeustheLunarchAbility2(state));
	}
}
