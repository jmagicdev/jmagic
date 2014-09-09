package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Telepathy")
@Types({Type.ENCHANTMENT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Telepathy extends Card
{
	public static final class RevealHands extends StaticAbility
	{
		public RevealHands(GameState state)
		{
			super(state, "Your opponents play with their hands revealed.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REVEAL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, InZone.instance(HandOf.instance(OpponentsOf.instance(ControllerOf.instance(This.instance())))));
			this.addEffectPart(part);
		}
	}

	public Telepathy(GameState state)
	{
		super(state);

		this.addAbility(new RevealHands(state));
	}
}
