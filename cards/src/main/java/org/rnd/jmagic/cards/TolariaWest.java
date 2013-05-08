package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tolaria West")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TolariaWest extends Card
{
	public TolariaWest(GameState state)
	{
		super(state);

		// Tolaria West enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// {T}: Add {U} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForU(state));

		// Transmute {1}{U}{U} ({1}{U}{U}, Discard this card: Search your
		// library for a card with converted mana cost 0, reveal it, and put it
		// into your hand. Then shuffle your library. Transmute only as a
		// sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Transmute(state, "(1)(U)(U)"));
	}
}
