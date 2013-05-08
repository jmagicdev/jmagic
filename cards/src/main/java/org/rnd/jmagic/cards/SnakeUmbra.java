package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Snake Umbra")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SnakeUmbra extends Card
{
	public static final class DamageEqualsDraw extends EventTriggeredAbility
	{
		public DamageEqualsDraw(GameState state)
		{
			super(state, "Whenever this creature deals damage to an opponent, you may draw a card.");
			this.addPattern(whenDealsDamageToAnOpponent(ABILITY_SOURCE_OF_THIS));
			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card."));
		}
	}

	public static final class SnakeUmbraAbility1 extends StaticAbility
	{
		public SnakeUmbraAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and has \"Whenever this creature deals damage to an opponent, you may draw a card.\"");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), DamageEqualsDraw.class));
		}
	}

	public SnakeUmbra(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+1 and has
		// "Whenever this creature deals damage to an opponent, you may draw a card."
		this.addAbility(new SnakeUmbraAbility1(state));

		// Totem armor (If enchanted creature would be destroyed, instead remove
		// all damage from it and destroy this Aura.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TotemArmor(state));
	}
}
