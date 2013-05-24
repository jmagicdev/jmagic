package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dakmor Salvage")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DakmorSalvage extends Card
{
	public DakmorSalvage(GameState state)
	{
		super(state);

		// Dakmor Salvage enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForB(state));

		// Dredge 2 (If you would draw a card, instead you may put exactly two
		// cards from the top of your library into your graveyard. If you do,
		// return this card from your graveyard to your hand. Otherwise, draw a
		// card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 2));
	}
}
