package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Plated Geopede")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class PlatedGeopede extends Card
{
	public PlatedGeopede(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForPump(state, this.getName(), +2, +2));
	}
}
