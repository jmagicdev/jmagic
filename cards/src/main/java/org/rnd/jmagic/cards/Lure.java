package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lure")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1GG")
@ColorIdentity({Color.GREEN})
public final class Lure extends Card
{
	// All creatures able to block enchanted creature do so.
	public static final class Taunt extends StaticAbility
	{
		public Taunt(GameState state)
		{
			super(state, "All creatures able to block enchanted creature do so.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT_FOR_EACH_DEFENDING_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, EnchantedBy.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, HasType.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public Lure(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new Taunt(state));
	}
}
