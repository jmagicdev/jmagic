package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Zealous Guardian")
@Types({Type.CREATURE})
@SubTypes({SubType.KITHKIN, SubType.SOLDIER})
@ManaCost("(WU)")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class ZealousGuardian extends Card
{
	public ZealousGuardian(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));
	}
}
