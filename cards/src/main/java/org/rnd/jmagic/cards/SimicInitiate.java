package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Simic Initiate")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MUTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SimicInitiate extends Card
{
	public SimicInitiate(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Graft(state, 1));
	}
}
