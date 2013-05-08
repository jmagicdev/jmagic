package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Virulent Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class VirulentSliver extends Card
{
	@Name("Poisonous 1")
	public static final class Poisonous1 extends org.rnd.jmagic.abilities.keywords.Poisonous
	{
		public Poisonous1(GameState state)
		{
			super(state, 1);
		}
	}

	public VirulentSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, Poisonous1.class, "All Sliver creatures have poisonous 1."));
	}
}
