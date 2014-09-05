package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Viashino Spearhunter")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VIASHINO})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ViashinoSpearhunter extends Card
{
	public ViashinoSpearhunter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
