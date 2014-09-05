package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Kingpin's Pet")
@Types({Type.CREATURE})
@SubTypes({SubType.THRULL})
@ManaCost("1WB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class KingpinsPet extends Card
{
	public KingpinsPet(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));
	}
}
