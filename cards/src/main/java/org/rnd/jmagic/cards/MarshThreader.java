package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Marsh Threader")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.KOR})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MarshThreader extends Card
{
	public MarshThreader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Swampwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
