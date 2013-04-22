package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dryad's Favor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class DryadsFavor extends Card
{
	public static final class DryadsFavorAbility1 extends StaticAbility
	{
		public DryadsFavorAbility1(GameState state)
		{
			super(state, "Enchanted creature has forestwalk.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Landwalk.Forestwalk.class));
		}
	}

	public DryadsFavor(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has forestwalk. (It's unblockable as long as
		// defending player controls a Forest.)
		this.addAbility(new DryadsFavorAbility1(state));
	}
}
