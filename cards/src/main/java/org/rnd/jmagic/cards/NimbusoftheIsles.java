package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nimbus of the Isles")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class NimbusoftheIsles extends Card
{
	public NimbusoftheIsles(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying (This creature can't be blocked except by creatures with
		// flying or reach.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
