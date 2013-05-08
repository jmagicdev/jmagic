package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Prodigal Pyromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.COMMON)})
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
