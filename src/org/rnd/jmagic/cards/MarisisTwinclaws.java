package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Marisi's Twinclaws")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.CAT})
@ManaCost("2(R/W)G")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class MarisisTwinclaws extends Card
{
	public MarisisTwinclaws(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));
	}
}
