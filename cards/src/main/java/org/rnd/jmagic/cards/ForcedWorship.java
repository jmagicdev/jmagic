package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Forced Worship")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ForcedWorship extends Card
{
	public static final class ForcedWorshipAbility1 extends StaticAbility
	{
		public ForcedWorshipAbility1(GameState state)
		{
			super(state, "Enchanted creature can't attack.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Attacking.instance(), enchantedCreature)));
			this.addEffectPart(part);
		}
	}

	public static final class ForcedWorshipAbility2 extends ActivatedAbility
	{
		public ForcedWorshipAbility2(GameState state)
		{
			super(state, "(2)(W): Return Forced Worship to its owner's hand.");
			this.setManaCost(new ManaPool("(2)(W)"));
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Forced Worship to its owner's hand."));
		}
	}

	public ForcedWorship(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature can't attack.
		this.addAbility(new ForcedWorshipAbility1(state));

		// (2)(W): Return Forced Worship to its owner's hand.
		this.addAbility(new ForcedWorshipAbility2(state));
	}
}
