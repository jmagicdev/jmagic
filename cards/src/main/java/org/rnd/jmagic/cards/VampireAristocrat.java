package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vampire Aristocrat")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.VAMPIRE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VampireAristocrat extends Card
{
	public VampireAristocrat(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Cannibalize(state, this.getName()));
	}
}
