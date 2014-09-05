package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Bogardan Lancer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BogardanLancer extends Card
{
	public BogardanLancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 1));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flanking(state));
	}
}
