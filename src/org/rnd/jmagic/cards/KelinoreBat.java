package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kelinore Bat")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class KelinoreBat extends Card
{
	public KelinoreBat(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
