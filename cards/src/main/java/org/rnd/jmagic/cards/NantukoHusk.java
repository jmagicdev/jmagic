package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;

@Name("Nantuko Husk")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT, SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class NantukoHusk extends Card
{
	public NantukoHusk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Cannibalize(state, this.getName()));
	}
}
