package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Devoted Druid")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.ELF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class DevotedDruid extends Card
{
	public static final class DevotedDruidAbility1 extends ActivatedAbility
	{
		public DevotedDruidAbility1(GameState state)
		{
			super(state, "Put a -1/-1 counter on Devoted Druid: Untap Devoted Druid.");
			this.addCost(putCountersOnThis(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, "Devoted Druid"));
		}
	}

	public DevotedDruid(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));

		// Put a -1/-1 counter on Devoted Druid: Untap Devoted Druid.
		this.addAbility(new DevotedDruidAbility1(state));
	}
}
