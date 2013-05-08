package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Anaba Bodyguard")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.HOMELANDS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class AnabaBodyguard extends Card
{
	public AnabaBodyguard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
