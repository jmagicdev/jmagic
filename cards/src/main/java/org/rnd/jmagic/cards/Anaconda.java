package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Anaconda")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class Anaconda extends Card
{
	public Anaconda(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Swampwalk (This creature is unblockable as long as defending player
		// controls a Swamp.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
