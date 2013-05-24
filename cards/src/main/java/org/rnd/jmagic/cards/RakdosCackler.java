package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rakdos Cackler")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("(B/R)")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosCackler extends Card
{
	public RakdosCackler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));
	}
}
