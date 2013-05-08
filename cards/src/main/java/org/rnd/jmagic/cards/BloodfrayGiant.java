package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bloodfray Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BloodfrayGiant extends Card
{
	public BloodfrayGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));
	}
}
