package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Abomination of Gudul")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("3BGU")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class AbominationofGudul extends Card
{
	public static final class AbominationofGudulAbility1 extends EventTriggeredAbility
	{
		public AbominationofGudulAbility1(GameState state)
		{
			super(state, "Whenever Abomination of Gudul deals combat damage to a player, you may draw a card. If you do, discard a card.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			this.addEffect(youMayDrawACardIfYouDoDiscardACard());
		}
	}

	public AbominationofGudul(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Abomination of Gudul deals combat damage to a player, you
		// may draw a card. If you do, discard a card.
		this.addAbility(new AbominationofGudulAbility1(state));

		// Morph (2)(B)(G)(U) (You may cast this card face down as a 2/2
		// creature for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(B)(G)(U)"));
	}
}
