package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Jeskai Elder")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class JeskaiElder extends Card
{
	public static final class JeskaiElderAbility1 extends EventTriggeredAbility
	{
		public JeskaiElderAbility1(GameState state)
		{
			super(state, "Whenever Jeskai Elder deals combat damage to a player, you may draw a card. If you do, discard a card.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(youMayDrawACardIfYouDoDiscardACard());
		}
	}

	public JeskaiElder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));

		// Whenever Jeskai Elder deals combat damage to a player, you may draw a
		// card. If you do, discard a card.
		this.addAbility(new JeskaiElderAbility1(state));
	}
}
