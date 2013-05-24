package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Glory Seeker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GlorySeeker extends Card
{
	public GlorySeeker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
