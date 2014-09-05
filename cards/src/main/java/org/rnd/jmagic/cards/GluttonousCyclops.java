package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gluttonous Cyclops")
@Types({Type.CREATURE})
@SubTypes({SubType.CYCLOPS})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = JourneyIntoNyx.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GluttonousCyclops extends Card
{
	public GluttonousCyclops(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.Monstrosity(state, "(5)(R)(R)", 3));

		this.setPower(5);
		this.setToughness(4);
	}
}
