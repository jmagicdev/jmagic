package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trollhide")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Trollhide extends Card
{
	public static final class Regenerate1G extends org.rnd.jmagic.abilities.Regenerate
	{
		public Regenerate1G(GameState state)
		{
			super(state, "(1)(G)", "this creature");
		}
	}

	public static final class TrollhideAbility1 extends StaticAbility
	{
		public TrollhideAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has \"(1)(G): Regenerate this creature.\"");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +2));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), Regenerate1G.class));
		}
	}

	public Trollhide(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has
		// "(1)(G): Regenerate this creature."
		this.addAbility(new TrollhideAbility1(state));
	}
}
