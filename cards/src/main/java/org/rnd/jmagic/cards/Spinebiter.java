package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spinebiter")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class Spinebiter extends Card
{
	public static final class SpinebiterAbility1 extends StaticAbility
	{
		public SpinebiterAbility1(GameState state)
		{
			super(state, "You may have Spinebiter assign its combat damage as though it weren't blocked.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DEAL_DAMAGE_AS_THOUGH_UNBLOCKED);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public Spinebiter(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// You may have Spinebiter assign its combat damage as though it weren't
		// blocked.
		this.addAbility(new SpinebiterAbility1(state));
	}
}
