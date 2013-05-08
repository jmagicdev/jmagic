package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Savage Silhouette")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SavageSilhouette extends Card
{
	public static final class Hulk extends StaticAbility
	{
		public static final class Regenerate extends org.rnd.jmagic.abilities.Regenerate
		{
			public Regenerate(GameState state)
			{
				super(state, "(1)(G)", "this creature");
			}
		}

		public Hulk(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has \"(1)(G): Regenerate this creature.\"");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, 2, 2));
			this.addEffectPart(addAbilityToObject(enchantedCreature, Regenerate.class));
		}
	}

	public SavageSilhouette(GameState state)
	{
		super(state);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new Hulk(state));
	}
}
