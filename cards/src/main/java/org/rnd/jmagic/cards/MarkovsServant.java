package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Markov's Servant")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MarkovsServant extends AlternateCard
{
	public MarkovsServant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.setColorIndicator(Color.BLACK);
	}
}
