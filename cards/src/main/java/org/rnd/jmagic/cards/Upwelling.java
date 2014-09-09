package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Upwelling")
@Types({Type.ENCHANTMENT})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class Upwelling extends Card
{
	public static final class CantManaBurn extends StaticAbility
	{
		public CantManaBurn(GameState state)
		{
			super(state, "Mana pools don't empty as steps and phases end.");

			SimpleEventPattern emptyEvent = new SimpleEventPattern(EventType.EMPTY_ALL_MANA_POOLS);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(emptyEvent));
			this.addEffectPart(part);
		}
	}

	public Upwelling(GameState state)
	{
		super(state);

		this.addAbility(new CantManaBurn(state));
	}
}
