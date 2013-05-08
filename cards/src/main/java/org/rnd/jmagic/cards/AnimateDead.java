package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Animate Dead")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class AnimateDead extends Card
{
	public static final class EnchantDeadCreature extends org.rnd.jmagic.abilities.keywords.Enchant
	{
		public EnchantDeadCreature(GameState state)
		{
			super(state, "creature card in a graveyard", Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))));
		}
	}

	public static final class EnchantAnimatedCreature extends org.rnd.jmagic.abilities.EnchantAnimatedCreature
	{
		public EnchantAnimatedCreature(GameState state)
		{
			super(state, "Animate Dead");
		}
	}

	public static final class AnimateDeadAbility2 extends StaticAbility
	{
		public AnimateDeadAbility2(GameState state)
		{
			super(state, "Enchanted creature gets -1/-0.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -1, -0));
		}
	}

	public AnimateDead(GameState state)
	{
		super(state);

		// Enchant creature card in a graveyard
		this.addAbility(new EnchantDeadCreature(state));

		// When Animate Dead enters the battlefield, if it's on the battlefield,
		// it loses "enchant creature card in a graveyard" and gains
		// "enchant creature put onto the battlefield with Animate Dead." Return
		// enchanted creature card to the battlefield under your control and
		// attach Animate Dead to it. When Animate Dead leaves the battlefield,
		// that creature's controller sacrifices it.
		this.addAbility(new org.rnd.jmagic.abilities.AnimateDeadCreature(state, this.getName(), EnchantDeadCreature.class, EnchantAnimatedCreature.class, false));

		// Enchanted creature gets -1/-0.
		this.addAbility(new AnimateDeadAbility2(state));
	}
}
