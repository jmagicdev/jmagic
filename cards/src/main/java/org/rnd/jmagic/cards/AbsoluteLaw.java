package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Absolute Law")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AbsoluteLaw extends Card
{
	public static final class AbsoluteLawAbility0 extends StaticAbility
	{
		public AbsoluteLawAbility0(GameState state)
		{
			super(state, "All creatures have protection from red.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CreaturePermanents.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Protection.FromRed.class)));
			this.addEffectPart(part);
		}
	}

	public AbsoluteLaw(GameState state)
	{
		super(state);

		// All creatures have protection from red.
		this.addAbility(new AbsoluteLawAbility0(state));
	}
}
