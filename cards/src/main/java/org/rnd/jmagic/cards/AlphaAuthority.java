package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Alpha Authority")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class AlphaAuthority extends Card
{
	public static final class AlphaAuthorityAbility1 extends StaticAbility
	{
		public AlphaAuthorityAbility1(GameState state)
		{
			super(state, "Enchanted creature has hexproof and can't be blocked by more than one creature.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());

			this.addEffectPart(addAbilityToObject(enchanted, org.rnd.jmagic.abilities.keywords.Hexproof.class));

			SetGenerator blockingWithMoreThanOneCreature = Intersect.instance(Between.instance(2, null), Count.instance(Blocking.instance(enchanted)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingWithMoreThanOneCreature));
			this.addEffectPart(part);
		}
	}

	public AlphaAuthority(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has hexproof and can't be blocked by more than one
		// creature.
		this.addAbility(new AlphaAuthorityAbility1(state));
	}
}
