package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Frenzy Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class FrenzySliver extends Card
{
	@Name("Frenzy 1")
	public static final class Frenzy1 extends org.rnd.jmagic.abilities.keywords.Frenzy
	{
		public Frenzy1(GameState state)
		{
			super(state, 1);
		}
	}

	public FrenzySliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, Frenzy1.class, "All Sliver creatures have frenzy 1."));
	}

}
