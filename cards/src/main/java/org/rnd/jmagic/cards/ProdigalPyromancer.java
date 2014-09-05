package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Prodigal Pyromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = PlanarChaos.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ProdigalPyromancer extends Card
{
	public ProdigalPyromancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.Ping(state, this.getName()));
	}
}
