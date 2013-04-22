package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mark of the Vampire")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MarkoftheVampire extends Card
{
	public static final class MarkoftheVampireAbility1 extends StaticAbility
	{
		public MarkoftheVampireAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has lifelink.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchanted, +2, +2));
			this.addEffectPart(addAbilityToObject(enchanted, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Lifelink.class)));
		}
	}

	public MarkoftheVampire(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has lifelink. (Damage dealt by the
		// creature also causes its controller to gain that much life.)
		this.addAbility(new MarkoftheVampireAbility1(state));
	}
}
