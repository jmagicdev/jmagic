package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Lost Leonin")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.SOLDIER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class LostLeonin extends Card
{
	public LostLeonin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
