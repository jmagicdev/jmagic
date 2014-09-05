package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ogre Resister")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
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
