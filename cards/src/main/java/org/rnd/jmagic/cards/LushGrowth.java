package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lush Growth")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class LushGrowth extends Card
{
	public static final class Lush extends StaticAbility
	{
		public Lush(GameState state)
		{
			super(state, "Enchanted land is a Mountain, Forest, and Plains.");

			SetGenerator enchantedLand = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedLand);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.MOUNTAIN, SubType.FOREST, SubType.PLAINS));
			this.addEffectPart(part);
		}
	}

	public LushGrowth(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		this.addAbility(new Lush(state));
	}
}
