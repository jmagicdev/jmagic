package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blazing Archon")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHON})
@ManaCost("6WWW")
@ColorIdentity({Color.WHITE})
public final class BlazingArchon extends Card
{
	public static final class BlazingArchonAbility1 extends StaticAbility
	{
		public BlazingArchonAbility1(GameState state)
		{
			super(state, "Creatures can't attack you.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			SetGenerator attackingYou = Attacking.instance(You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(attackingYou));
			this.addEffectPart(part);
		}
	}

	public BlazingArchon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Creatures can't attack you.
		this.addAbility(new BlazingArchonAbility1(state));
	}
}
