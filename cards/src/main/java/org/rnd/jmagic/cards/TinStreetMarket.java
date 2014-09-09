package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tin Street Market")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class TinStreetMarket extends Card
{
	public static final class TinStreetMarketAbility1 extends StaticAbility
	{
		public static final class DiscardDraw extends ActivatedAbility
		{
			public DiscardDraw(GameState state)
			{
				super(state, "(T), Discard a card: Draw a card.");
				this.costsTap = true;
				this.addCost(discardCards(You.instance(), 1, "Discard a card"));
				this.addEffect(drawCards(You.instance(), 1, "Draw a card."));
			}
		}

		public TinStreetMarketAbility1(GameState state)
		{
			super(state, "Enchanted land has \"(T), Discard a card: Draw a card.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), DiscardDraw.class));
		}
	}

	public TinStreetMarket(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land has "(T), Discard a card: Draw a card."
		this.addAbility(new TinStreetMarketAbility1(state));
	}
}
