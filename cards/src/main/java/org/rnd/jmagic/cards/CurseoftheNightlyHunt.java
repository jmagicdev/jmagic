package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Curse of the Nightly Hunt")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class CurseoftheNightlyHunt extends Card
{
	public static final class CurseoftheNightlyHuntAbility1 extends StaticAbility
	{
		public CurseoftheNightlyHuntAbility1(GameState state)
		{
			super(state, "Creatures enchanted player controls attack each turn if able.");

			SetGenerator controlledByEnchantedPlayer = ControlledBy.instance(EnchantedBy.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, Intersect.instance(CreaturePermanents.instance(), controlledByEnchantedPlayer));
			this.addEffectPart(part);
		}
	}

	public CurseoftheNightlyHunt(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// Creatures enchanted player controls attack each turn if able.
		this.addAbility(new CurseoftheNightlyHuntAbility1(state));
	}
}
