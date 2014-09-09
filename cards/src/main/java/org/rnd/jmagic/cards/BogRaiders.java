package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bog Raiders")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class BogRaiders extends Card
{
	public BogRaiders(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Swampwalk (This creature is unblockable as long as defending player
		// controls a Swamp.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
