package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Stampeding Rhino")
@Types({Type.CREATURE})
@SubTypes({SubType.RHINO})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class StampedingRhino extends Card
{
	public StampedingRhino(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
