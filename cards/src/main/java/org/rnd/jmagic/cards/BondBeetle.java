package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bond Beetle")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BondBeetle extends Card
{
	public static final class BondBeetleAbility0 extends EventTriggeredAbility
	{
		public BondBeetleAbility0(GameState state)
		{
			super(state, "When Bond Beetle enters the battlefield, put a +1/+1 counter on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature."));
		}
	}

	public BondBeetle(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// When Bond Beetle enters the battlefield, put a +1/+1 counter on
		// target creature.
		this.addAbility(new BondBeetleAbility0(state));
	}
}
