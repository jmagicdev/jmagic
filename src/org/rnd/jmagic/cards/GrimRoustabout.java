package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Grim Roustabout")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON, SubType.WARRIOR})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GrimRoustabout extends Card
{
	public GrimRoustabout(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));

		// (1)(B): Regenerate Grim Roustabout.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(1)(B)", this.getName()));
	}
}
