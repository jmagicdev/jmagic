package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shimmering Wings")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ShimmeringWings extends Card
{
	public static final class Shimmer extends StaticAbility
	{
		public Shimmer(GameState state)
		{
			super(state, "Enchanted creature has flying.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public static final class Return extends ActivatedAbility
	{
		public Return(GameState state)
		{
			super(state, "(U): Return Shimmering Wings to its owner's hand.");

			this.setManaCost(new ManaPool("U"));

			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Shimmering Wings to its owner's hand."));
		}
	}

	public ShimmeringWings(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new Shimmer(state));
		this.addAbility(new Return(state));
	}
}
