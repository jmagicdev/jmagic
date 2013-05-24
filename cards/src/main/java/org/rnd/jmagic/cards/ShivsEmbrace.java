package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shiv's Embrace")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ShivsEmbrace extends Card
{
	public static final class ShivsEmbraceAbility1 extends StaticAbility
	{
		public ShivsEmbraceAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has flying.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +2));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public static final class ShivsEmbraceAbility2 extends ActivatedAbility
	{
		public ShivsEmbraceAbility2(GameState state)
		{
			super(state, "(R): Enchanted creature gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), +1, +0, "Enchanted creature gets +1/+0 until end of turn."));
		}
	}

	public ShivsEmbrace(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has flying.
		this.addAbility(new ShivsEmbraceAbility1(state));

		// (R): Enchanted creature gets +1/+0 until end of turn.
		this.addAbility(new ShivsEmbraceAbility2(state));
	}
}
