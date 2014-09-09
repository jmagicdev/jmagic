package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lymph Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class LymphSliver extends Card
{
	@Name("Absorb 1")
	public static final class Absorb1 extends org.rnd.jmagic.abilities.keywords.Absorb
	{
		public Absorb1(GameState state)
		{
			super(state, 1);
		}
	}

	public LymphSliver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, Absorb1.class, "All Sliver creatures have absorb 1."));
	}
}
