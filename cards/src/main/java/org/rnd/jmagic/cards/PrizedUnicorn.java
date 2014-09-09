package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Prized Unicorn")
@Types({Type.CREATURE})
@SubTypes({SubType.UNICORN})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class PrizedUnicorn extends Card
{
	public static final class Taunt extends StaticAbility
	{
		public Taunt(GameState state)
		{
			super(state, "All creatures able to block Prized Unicorn do so.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT_FOR_EACH_DEFENDING_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, HasType.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public PrizedUnicorn(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// All creatures able to block Prized Unicorn do so.
		this.addAbility(new Taunt(state));
	}
}
