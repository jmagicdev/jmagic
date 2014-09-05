package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Markov Patrician")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MarkovPatrician extends Card
{
	public MarkovPatrician(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
