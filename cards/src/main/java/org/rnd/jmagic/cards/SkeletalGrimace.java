package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Skeletal Grimace")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SkeletalGrimace extends Card
{
	public static final class SkeletalGrimaceAbility1 extends StaticAbility
	{
		public static final class RegenerateB extends org.rnd.jmagic.abilities.Regenerate
		{
			public RegenerateB(GameState state)
			{
				super(state, "(B)", "this creature");
			}
		}

		public SkeletalGrimaceAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and has \"(B): Regenerate this creature.\"");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +1, +1));
			this.addEffectPart(addAbilityToObject(enchantedCreature, RegenerateB.class));
		}
	}

	public SkeletalGrimace(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+1 and has
		// "(B): Regenerate this creature."
		this.addAbility(new SkeletalGrimaceAbility1(state));
	}
}
