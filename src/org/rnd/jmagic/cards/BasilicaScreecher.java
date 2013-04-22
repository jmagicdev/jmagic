package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Basilica Screecher")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BasilicaScreecher extends Card
{
	public BasilicaScreecher(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));
	}
}
