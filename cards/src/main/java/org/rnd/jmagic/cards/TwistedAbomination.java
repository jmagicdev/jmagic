package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Twisted Abomination")
@Types({Type.CREATURE})
@SubTypes({SubType.MUTANT, SubType.ZOMBIE})
@ManaCost("5B")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.SPECIAL), @Printings.Printed(ex = Scourge.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class TwistedAbomination extends Card
{
	public TwistedAbomination(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// (B): Regenerate Twisted Abomination.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", this.getName()));

		// Swampcycling (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.SwampCycling(state, "(2)"));
	}
}
