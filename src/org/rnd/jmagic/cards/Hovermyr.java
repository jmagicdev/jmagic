package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hovermyr")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Hovermyr extends Card
{
	public Hovermyr(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
