package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ogre Sentry")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.OGRE})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class OgreSentry extends Card
{
	public OgreSentry(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
	}
}
