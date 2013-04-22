package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Noble Hierarch")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class NobleHierarch extends Card
{
	public static final class TapForBant extends org.rnd.jmagic.abilities.TapForMana
	{
		public TapForBant(GameState state)
		{
			super(state, "(GWU)");
		}
	}

	public NobleHierarch(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		// (T): Add (G), (W), or (U) to your mana pool.
		this.addAbility(new TapForBant(state));
	}
}
