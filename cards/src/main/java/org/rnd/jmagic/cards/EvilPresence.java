package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Evil Presence")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class EvilPresence extends Card
{
	public static final class EvilPresenceAbility1 extends StaticAbility
	{
		public EvilPresenceAbility1(GameState state)
		{
			super(state, "Enchanted land is a Swamp.");

			SetGenerator enchantedLand = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedLand);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.SWAMP));
			this.addEffectPart(part);
		}
	}

	public EvilPresence(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land is a Swamp.
		this.addAbility(new EvilPresenceAbility1(state));
	}
}
