package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Jwari Scuttler")
@Types({Type.CREATURE})
@SubTypes({SubType.CRAB})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class JwariScuttler extends Card
{
	public JwariScuttler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
	}
}
