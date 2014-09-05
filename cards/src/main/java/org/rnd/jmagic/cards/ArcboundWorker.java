package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Arcbound Worker")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Darksteel.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ArcboundWorker extends Card
{
	public ArcboundWorker(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Modular 1
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Modular(state, 1));
	}
}
