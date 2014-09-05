package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Burden of Guilt")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BurdenofGuilt extends Card
{
	public static final class BurdenofGuiltAbility1 extends ActivatedAbility
	{
		public BurdenofGuiltAbility1(GameState state)
		{
			super(state, "(1): Tap enchanted creature.");
			this.setManaCost(new ManaPool("(1)"));

			this.addEffect(tap(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Tap enchanted creature"));
		}
	}

	public BurdenofGuilt(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// (1): Tap enchanted creature.
		this.addAbility(new BurdenofGuiltAbility1(state));
	}
}
