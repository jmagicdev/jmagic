package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Pincher Beetles")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class PincherBeetles extends Card
{
	public PincherBeetles(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));
	}
}
