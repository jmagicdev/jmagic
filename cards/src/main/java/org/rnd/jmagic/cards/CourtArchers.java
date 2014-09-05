package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Court Archers")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ARCHER})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CourtArchers extends Card
{
	public CourtArchers(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
