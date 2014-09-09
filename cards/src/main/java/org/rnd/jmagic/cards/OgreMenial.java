package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ogre Menial")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class OgreMenial extends Card
{
	public OgreMenial(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// (R): Ogre Menial gets +1/+0 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, this.getName()));
	}
}
