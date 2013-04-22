package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hover Barrier")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL, SubType.ILLUSION})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class HoverBarrier extends Card
{
	public HoverBarrier(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(6);

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
