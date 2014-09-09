package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Farbog Explorer")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.HUMAN})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class FarbogExplorer extends Card
{
	public FarbogExplorer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Swampwalk (This creature is unblockable as long as defending player
		// controls a Swamp.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
