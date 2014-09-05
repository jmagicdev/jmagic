package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Syndic of Tithes")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SyndicofTithes extends Card
{
	public SyndicofTithes(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));
	}
}
