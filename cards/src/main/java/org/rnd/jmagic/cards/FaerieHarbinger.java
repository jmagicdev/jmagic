package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Faerie Harbinger")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.WIZARD})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class FaerieHarbinger extends Card
{
	public FaerieHarbinger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Faerie Harbinger enters the battlefield, you may search your
		// library for a Faerie card, reveal it, then shuffle your library and
		// put that card on top of it.
		this.addAbility(new org.rnd.jmagic.abilities.HarbingerAbility(state, this.getName(), SubType.FAERIE));
	}
}
