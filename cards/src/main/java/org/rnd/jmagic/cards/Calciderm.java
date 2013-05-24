package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Calciderm")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Calciderm extends Card
{
	public Calciderm(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Shroud
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));

		// Vanishing 4
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vanishing(state, 4));
	}
}
