package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Drift of Phantasms")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class DriftofPhantasms extends Card
{
	public DriftofPhantasms(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Transmute {1}{U}{U} ({1}{U}{U}, Discard this card: Search your
		// library for a card with the same converted mana cost as this card,
		// reveal it, and put it into your hand. Then shuffle your library.
		// Transmute only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Transmute(state, "(1)(U)(U)"));
	}
}
