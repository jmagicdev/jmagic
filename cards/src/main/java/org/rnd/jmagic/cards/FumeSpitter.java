package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fume Spitter")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class FumeSpitter extends Card
{
	public static final class FumeSpitterAbility0 extends ActivatedAbility
	{
		public FumeSpitterAbility0(GameState state)
		{
			super(state, "Sacrifice Fume Spitter: Put a -1/-1 counter on target creature.");
			this.addCost(sacrificeThis("Fume Spitter"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature."));
		}
	}

	public FumeSpitter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice Fume Spitter: Put a -1/-1 counter on target creature.
		this.addAbility(new FumeSpitterAbility0(state));
	}
}
