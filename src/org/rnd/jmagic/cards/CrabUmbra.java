package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crab Umbra")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class CrabUmbra extends Card
{
	public static final class CrabUmbraAbility1 extends ActivatedAbility
	{
		public CrabUmbraAbility1(GameState state)
		{
			super(state, "(2)(U): Untap enchanted creature.");

			this.setManaCost(new ManaPool("(2)(U)"));

			this.addEffect(untap(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Untap enchanted creature."));
		}
	}

	public CrabUmbra(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// (2)(U): Untap enchanted creature.
		this.addAbility(new CrabUmbraAbility1(state));

		// Totem armor
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TotemArmor(state));
	}
}
