package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Underworld Connections")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class UnderworldConnections extends Card
{
	public static final class UnderworldConnectionsAbility1 extends StaticAbility
	{
		public static final class PayLifeDrawCard extends ActivatedAbility
		{
			public PayLifeDrawCard(GameState state)
			{
				super(state, "(T), Pay 1 life: Draw a card.");
				this.costsTap = true;

				this.addCost(payLife(You.instance(), 1, "Pay 1 life."));
				this.addEffect(drawCards(You.instance(), 1, "Draw a card."));
			}
		}

		public UnderworldConnectionsAbility1(GameState state)
		{
			super(state, "Enchanted land has \"(T), Pay 1 life: Draw a card.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), PayLifeDrawCard.class));
		}
	}

	public UnderworldConnections(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land has "(T), Pay 1 life: Draw a card."
		this.addAbility(new UnderworldConnectionsAbility1(state));
	}
}
