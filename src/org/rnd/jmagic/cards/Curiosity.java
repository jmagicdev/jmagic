package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Curiosity")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Curiosity extends Card
{
	public static final class CuriosityAbility1 extends EventTriggeredAbility
	{
		public CuriosityAbility1(GameState state)
		{
			super(state, "Whenever enchanted creature deals damage to an opponent, you may draw a card.");
			this.addPattern(whenDealsDamageToAnOpponent(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card"));
		}
	}

	public Curiosity(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Whenever enchanted creature deals damage to an opponent, you may draw
		// a card.
		this.addAbility(new CuriosityAbility1(state));
	}
}
