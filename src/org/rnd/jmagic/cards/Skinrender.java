package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skinrender")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Skinrender extends Card
{
	public static final class SkinrenderAbility0 extends EventTriggeredAbility
	{
		public SkinrenderAbility0(GameState state)
		{
			super(state, "When Skinrender enters the battlefield, put three -1/-1 counters on target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(3, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put three -1/-1 counters on target creature."));
		}
	}

	public Skinrender(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Skinrender enters the battlefield, put three -1/-1 counters on
		// target creature.
		this.addAbility(new SkinrenderAbility0(state));
	}
}
