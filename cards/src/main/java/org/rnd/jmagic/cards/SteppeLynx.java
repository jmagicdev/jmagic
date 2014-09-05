package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Steppe Lynx")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SteppeLynx extends Card
{
	public SteppeLynx(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.LandfallForPump(state, this.getName(), +2, +2));
	}
}
