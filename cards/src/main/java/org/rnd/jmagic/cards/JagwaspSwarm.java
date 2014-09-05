package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Jagwasp Swarm")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class JagwaspSwarm extends Card
{
	public JagwaspSwarm(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
