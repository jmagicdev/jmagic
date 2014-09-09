package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;

@Name("Soul Warden")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class SoulWarden extends Card
{
	public SoulWarden(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new WardenTrigger(state));
	}
}
