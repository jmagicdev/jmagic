package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sea's Claim")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class SeasClaim extends Card
{
	public static final class Claim extends StaticAbility
	{
		public Claim(GameState state)
		{
			super(state, "Enchanted land is an Island.");

			SetGenerator enchantedLand = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedLand);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ISLAND));
			this.addEffectPart(part);
		}
	}

	public SeasClaim(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		this.addAbility(new Claim(state));
	}
}
