package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Drakewing Krasis")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE, SubType.LIZARD})
@ManaCost("1GU")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class DrakewingKrasis extends Card
{
	public DrakewingKrasis(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
