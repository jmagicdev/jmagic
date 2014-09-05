package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Furyborn Hellkite")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RRR")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class FurybornHellkite extends Card
{
	public FurybornHellkite(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Bloodthirst 6 (If an opponent was dealt damage this turn, this
		// creature enters the battlefield with six +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 6));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
