package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lux Cannon")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class LuxCannon extends Card
{
	public static final class LuxCannonAbility0 extends ActivatedAbility
	{
		public LuxCannonAbility0(GameState state)
		{
			super(state, "(T): Put a charge counter on Lux Cannon.");
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Lux Cannon."));
		}
	}

	public static final class LuxCannonAbility1 extends ActivatedAbility
	{
		public LuxCannonAbility1(GameState state)
		{
			super(state, "(T), Remove three charge counters from Lux Cannon: Destroy target permanent.");
			this.costsTap = true;
			this.addCost(removeCounters(3, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove three charge counters from Lux Cannon"));
			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			this.addEffect(destroy(target, "Destroy target permanent."));
		}
	}

	public LuxCannon(GameState state)
	{
		super(state);

		// (T): Put a charge counter on Lux Cannon.
		this.addAbility(new LuxCannonAbility0(state));

		// (T), Remove three charge counters from Lux Cannon: Destroy target
		// permanent.
		this.addAbility(new LuxCannonAbility1(state));
	}
}
