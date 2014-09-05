package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Nightshade Stinger")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.ROGUE})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class NightshadeStinger extends Card
{
	public NightshadeStinger(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Nightshade Stinger can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));
	}
}
