package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Thrun, the Last Troll")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.TROLL, SubType.SHAMAN})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class ThruntheLastTroll extends Card
{
	public ThruntheLastTroll(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Thrun, the Last Troll can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Hexproof
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// (1)(G): Regenerate Thrun.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(1)(G)", this.getName()));
	}
}
