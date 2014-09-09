package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ogre Resister")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class OgreResister extends Card
{
	public OgreResister(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);
	}
}
