package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Merfolk of the Depths")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.SOLDIER})
@ManaCost("4(G/U)(G/U)")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class MerfolkoftheDepths extends Card
{
	public MerfolkoftheDepths(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));
	}
}
