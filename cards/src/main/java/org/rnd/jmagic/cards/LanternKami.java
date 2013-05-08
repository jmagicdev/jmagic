package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lantern Kami")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class LanternKami extends Card
{
	public LanternKami(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
