package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Knight of Sursi")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KnightofSursi extends Card
{
	public KnightofSursi(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying; flanking
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flanking(state));

		// Suspend 3\u2014(W)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Suspend(state, 3, "(W)"));
	}
}
