package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Primal Huntbeast")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class PrimalHuntbeast extends Card
{
	public PrimalHuntbeast(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
	}
}
