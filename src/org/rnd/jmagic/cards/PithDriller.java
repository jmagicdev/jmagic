package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pith Driller")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HORROR})
@ManaCost("4(B/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PithDriller extends Card
{
	public static final class PithDrillerAbility0 extends EventTriggeredAbility
	{
		public PithDrillerAbility0(GameState state)
		{
			super(state, "When Pith Driller enters the battlefield, put a -1/-1 counter on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature."));
		}
	}

	public PithDriller(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// When Pith Driller enters the battlefield, put a -1/-1 counter on
		// target creature.
		this.addAbility(new PithDrillerAbility0(state));
	}
}
