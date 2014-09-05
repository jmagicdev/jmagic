package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Scourge Servant")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ScourgeServant extends Card
{
	public ScourgeServant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
