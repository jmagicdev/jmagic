package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ogre Sentry")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.OGRE})
@ManaCost("1R")
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
