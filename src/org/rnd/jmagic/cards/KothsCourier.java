package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Koth's Courier")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class KothsCourier extends Card
{
	public KothsCourier(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Forestwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Forestwalk(state));
	}
}
