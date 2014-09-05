package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Unruly Mob")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class UnrulyMob extends Card
{
	public static final class UnrulyMobAbility0 extends EventTriggeredAbility
	{
		public UnrulyMobAbility0(GameState state)
		{
			super(state, "Whenever another creature you control dies, put a +1/+1 counter on Unruly Mob.");
			this.addPattern(whenXDies(RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS)));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Unruly Mob."));
		}
	}

	public UnrulyMob(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever another creature you control dies, put a +1/+1 counter on
		// Unruly Mob.
		this.addAbility(new UnrulyMobAbility0(state));
	}
}
